package com.dhobi.perfectdhobi.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.databinding.FragmentLocationBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener

class LocationFragment : Fragment() {
    lateinit var fragmentLocationBinding: FragmentLocationBinding
    lateinit var mainActivity: MainActivity
    private var mLastLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var locationRequest: LocationRequest? = null
    var locationManager: LocationManager? = null
    var latitude: String? = ""
    var longitude: String? = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentLocationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_location, container, false)
        val root = fragmentLocationBinding.root
        mainActivity = activity as MainActivity

        fusedLocationClient =
            LocationServices.getFusedLocationProviderClient(mainActivity)

        locationManager =
            mainActivity.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager


        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(fragmentLocationBinding) {

            topnav.tvNavtitle.text="Change Location"
            topnav.ivBack.setOnClickListener {
                mainActivity.onBackPressedDispatcher.onBackPressed()
            }
            btnSelectLocation.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_address)
            }
            btnAddLocation.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_add_address)
            }
            btnCurrentLocation.setOnClickListener {
                if (ContextCompat.checkSelfPermission(
                        mainActivity,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    requestPermissionLauncher.launch(
                        arrayOf(
                            Manifest.permission.ACCESS_FINE_LOCATION
                        )
                    )
                } else {
                    displayLocationSettingsRequest(mainActivity)
                }
            }


        }
    }


    private fun displayLocationSettingsRequest(context: Context) {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()
        locationRequest = LocationRequest.create()
        locationRequest!!.priority =
            com.google.android.gms.location.LocationRequest.PRIORITY_HIGH_ACCURACY
        locationRequest!!.interval = 10000
        locationRequest!!.fastestInterval = 10000 / 2
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest!!)
        builder.setAlwaysShow(true)
        val resultPending: PendingResult<LocationSettingsResult> =
            LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build())
        resultPending.setResultCallback(object : ResultCallback<LocationSettingsResult?> {
            @SuppressLint("MissingPermission")
            override fun onResult(result: LocationSettingsResult) {
                val status: Status = result.status
                when (status.statusCode) {
                    LocationSettingsStatusCodes.SUCCESS -> {
                        Log.i(
                            "TAG",
                            "All location settings are satisfied."
                        )

                        fusedLocationClient.getCurrentLocation(
                            Priority.PRIORITY_HIGH_ACCURACY,
                            object : CancellationToken() {
                                override fun onCanceledRequested(p0: OnTokenCanceledListener) =
                                    CancellationTokenSource().token

                                override fun isCancellationRequested() = false
                            })
                            .addOnSuccessListener { location: Location? ->
                                if (location == null)
                                    Toast.makeText(
                                        mainActivity,
                                        "Cannot get location.",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                else {
                                    mLastLocation = location
                                    val lat = location.latitude
                                    val lon = location.longitude
                                    Toast.makeText(
                                        mainActivity,
                                        "Lat: $lat  Long: $lon",
                                        Toast.LENGTH_SHORT
                                    ).show()

                                    // Shared_Preferences.setLat(lat.toString())
                                    // Shared_Preferences.setLong(lon.toString())
                                    /*val intent = Intent(
                                        mainActivity,
                                        MainActivity::class.java
                                    )
                                    startActivity(intent)*/


//                                    reverseGeocoding(
//                                        location.latitude.toString(),
//                                        location.longitude.toString()
//                                    )

                                    //fusedLocationClient.removeLocationUpdates(location)
                                    Log.i(
                                        "TAG",
                                        "Lat: $lat  Long: $lon"
                                    )
                                }

                            }
                    }
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Log.i(
                            "TAG",
                            "Location settings are not satisfied. Show the user a dialog to upgrade location settings "
                        )
                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            val intentSenderRequest = IntentSenderRequest
                                .Builder(status.resolution!!).build()
                            resolutionForResult.launch(intentSenderRequest)
                            /*status.startResolutionForResult(
                                this@LocationActivity,
                                11
                            )*/
                        } catch (e: IntentSender.SendIntentException) {
                            Log.i("TAG", "PendingIntent unable to execute request.")
                        }
                    }
                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> Log.i(
                        "TAG",
                        "Location settings are inadequate, and cannot be fixed here. Dialog not created."
                    )
                }
            }
        })
    }

    private val resolutionForResult = registerForActivityResult(
        ActivityResultContracts.StartIntentSenderForResult()
    ) { activityResult ->

        if (activityResult.resultCode == RESULT_OK)
            displayLocationSettingsRequest(mainActivity)
    }

    @SuppressLint("MissingPermission")
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            displayLocationSettingsRequest(mainActivity)

        } else {
            // PERMISSION NOT GRANTED
        }
    }
}