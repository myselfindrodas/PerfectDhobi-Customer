package com.dhobi.perfectdhobi.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity.RESULT_CANCELED
import android.app.Activity.RESULT_OK
import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.AddAddressModel.AddAddressRequestModel
import com.dhobi.perfectdhobi.data.model.EditAddressModel.EditAddressRequestModel.EditAddressRequestModel
import com.dhobi.perfectdhobi.data.model.UpdateAddressModel.UpdateAddressRequestModel
import com.dhobi.perfectdhobi.data.model.address.Addres
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentAddAddressBinding
import com.dhobi.perfectdhobi.ui.Login
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.utils.Shared_Preferences
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.common.api.PendingResult
import com.google.android.gms.common.api.ResultCallback
import com.google.android.gms.common.api.Status
import com.google.android.gms.location.*
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.Autocomplete
import com.google.android.libraries.places.widget.AutocompleteActivity
import com.google.android.libraries.places.widget.model.AutocompleteActivityMode
import java.io.Serializable

class AddAddressFragment : Fragment() {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentAddAddressBinding: FragmentAddAddressBinding
    private lateinit var viewModel: CommonViewModel

    private var mLastLocation: Location? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    var locationRequest: LocationRequest? = null
    var locationManager: LocationManager? = null
    var latitude: String? = ""
    var longitude: String? = ""
    private val AUTOCOMPLETE_REQUEST_CODE = 1
    var placesClient: PlacesClient? = null
    private var editable = ""
    private var addressid = ""

    //    private var addressType = ""
//    var addressData: Addres? = null
//    var isEdited = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAddAddressBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_add_address, container, false)
        val root = fragmentAddAddressBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        val intent = arguments
        if (intent != null && intent.containsKey("viewtype")) {
            editable = intent.getString("viewtype", "")
        }
        if (intent != null && intent.containsKey("addressid")) {
            addressid = intent.getString("addressid", "")
        }

//        val intent = arguments
//        if (intent != null && intent.containsKey("viewtype")) {
////            addressData = getDataSerializable(intent, "data", Addres::class.java)
//////            println(addressData!!.houseNo)
////            isEdited = true
//        }

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(mainActivity)

        if (!Places.isInitialized()) {
            Places.initialize(mainActivity, getString(R.string.api_key))
        }
        placesClient = Places.createClient(mainActivity)


        locationManager = mainActivity.getSystemService(AppCompatActivity.LOCATION_SERVICE) as LocationManager




        with(fragmentAddAddressBinding) {

            btnAddAddress.text = "ADD LOCATION"
            if (editable.equals("edit")){
                btnAddAddress.text = "UPDATE ADDRESS"
                editAddress()
            }


            topnav.tvNavtitle.text = "Back"

//            if (addressData != null) {
//                addressData?.let { itAddressData ->
////                    etHouseNo.setText(itAddressData.houseNo)
////                    etApartmentName.setText(itAddressData.apartmentName)
////                    etAreaDetails.setText(itAddressData.area)
////                    etStreeDetails.setText(itAddressData.streetDetails)
////                    etCity.setText(itAddressData.city)
////                    etPinCode.setText(itAddressData.pincode)
//                    btnAddAddress.text = "Edit Address"
//                    topnav.tvNavtitle.text = "Edit address"
//
//                }
//            }


            topnav.ivBack.setOnClickListener {

                mainActivity.onBackPressedDispatcher.onBackPressed()
            }


            btnCurrentLocation.setOnClickListener {

                mainActivity.showProgressDialog()
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


            btnSearchLocation.setOnClickListener {
                openSearchBar()
            }

            btnAddAddress.setOnClickListener {
                if (!validate(fragmentAddAddressBinding)) {
                    return@setOnClickListener
                }
                if (editable.equals("edit")){
                    updateAddress(it)
                }else{
                    addAddress(
                        latitude.toString(),
                        longitude.toString(),
                        etHouseNo.text.toString(),
                        etApartmentName.text.toString(),
                        etCity.text.toString(),
                        etAreaDetails.text.toString(),
                        etOtherType.text.toString(),
                        etPinCode.text.toString(),
                        etStreeDetails.text.toString(),
                        it)
                }

            }
            /*btnAddAddress.setOnClickListener {
                if (!validate(fragmentAddAddressBinding)) {
                    return@setOnClickListener
                }
                if (addressType == "Other")
                    addressType = etOtherType.text.toString().trim()

                val addAddressRequestModel = if (isEdited) {
                    AddAddressRequestModel(
                        addressData?.id!!,
                        etHouseNo.text.toString().trim(),
                        etApartmentName.text.toString().trim(),
                        etStreeDetails.text.toString().trim(),
                        etAreaDetails.text.toString().trim(),
                        etCity.text.toString().trim(),
                        etPinCode.text.toString().trim(),
                        addressType
                    )
                } else {
                    AddAddressRequestModel(
                        0,
                        etHouseNo.text.toString().trim(),
                        etApartmentName.text.toString().trim(),
                        etStreeDetails.text.toString().trim(),
                        etAreaDetails.text.toString().trim(),
                        etCity.text.toString().trim(),
                        etPinCode.text.toString().trim(),
                        addressType
                    )
                }
                if (isEdited) {
                    postEditAddress(it, addAddressRequestModel)
                } else {
                    postAddAddress(it, addAddressRequestModel)
                }
            }*/

        }
    }


    private fun openSearchBar() {

        val fields = listOf(Place.Field.ID, Place.Field.ADDRESS, Place.Field.LAT_LNG)
        val intent = Autocomplete.IntentBuilder(AutocompleteActivityMode.FULLSCREEN, fields)
            .build(mainActivity)
        resolutionForPlaceResult.launch(intent)
        //startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE)
    }


    private fun displayLocationSettingsRequest(context: Context) {
        val googleApiClient = GoogleApiClient.Builder(context)
            .addApi(LocationServices.API).build()
        googleApiClient.connect()
        locationRequest = LocationRequest.create()
        locationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
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
                                    latitude = location.latitude.toString()
                                    longitude = location.longitude.toString()
//                                    Toast.makeText(
//                                        mainActivity,
//                                        "Lat: $lat  Long: $lon",
//                                        Toast.LENGTH_SHORT
//                                    ).show()
//
//                                    Shared_Preferences.setLat(lat.toString())
//                                    Shared_Preferences.setLong(lon.toString())
                                    mainActivity.hideProgressDialog()


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


    private val resolutionForPlaceResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->

            if (activityResult.resultCode == RESULT_OK) {

                val place = Autocomplete.getPlaceFromIntent(activityResult.data!!)
                val address = place.address

                fragmentAddAddressBinding.btnSearchLocation.text = address
                val lati = place.latLng!!.latitude.toString() + ""
                val longi = place.latLng!!.longitude.toString() + ""
                latitude = lati
                longitude = longi

                /*  val lat = location.latitude
                  val lon = location.longitude*/
                Toast.makeText(
                    mainActivity,
                    "Lat: $lati  Long: $longi",
                    Toast.LENGTH_SHORT
                ).show()

//            Shared_Preferences.setLat(lati)
//            Shared_Preferences.setLong(longi)
                /*activityRegistrationDetailsBinding.etLocation.setText(address)*/

            } else if (activityResult.resultCode == AutocompleteActivity.RESULT_ERROR) {

                val status = Autocomplete.getStatusFromIntent(activityResult.data!!)
            } else if (activityResult.resultCode == RESULT_CANCELED) {
// The user canceled the operation.
            }
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


//    private fun <T : Serializable?> getDataSerializable(
//        @Nullable bundle: Bundle?,
//        @Nullable key: String?,
//        clazz: Class<T>
//    ): T? {
//        if (bundle != null) {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//                return bundle.getSerializable(key, clazz)
//            } else {
//                try {
//                    return bundle.getSerializable(key) as T?
//                } catch (ignored: Throwable) {
//                }
//            }
//        }
//        return null
//    }

    private fun validate(mFragmentAddAddressBinding: FragmentAddAddressBinding): Boolean {
        with(mFragmentAddAddressBinding) {

            if (latitude.toString().length == 0 && longitude.toString().length == 0) {
                Toast.makeText(
                    mainActivity,
                    "Select Manual Address or Current Location!",
                    Toast.LENGTH_SHORT
                ).show()

                return false
            } else if (etHouseNo.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter House no.", Toast.LENGTH_SHORT).show()
                etHouseNo.requestFocus()
                return false
            } else if (etApartmentName.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Apertment name.", Toast.LENGTH_SHORT).show()
                etApartmentName.requestFocus()
                return false
            } else if (etStreeDetails.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Street details.", Toast.LENGTH_SHORT).show()
                etHouseNo.requestFocus()
                return false
            } else if (etAreaDetails.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Area Details.", Toast.LENGTH_SHORT).show()
                etAreaDetails.requestFocus()
                return false
            } else if (etCity.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter city name.", Toast.LENGTH_SHORT).show()
                etCity.requestFocus()
                return false
            } else if (etPinCode.text.toString().isEmpty()) {
                Toast.makeText(mainActivity, "Enter Pin code.", Toast.LENGTH_SHORT).show()
                etPinCode.requestFocus()
                return false
            } else if (etOtherType.text.toString().isEmpty()) {
                Toast.makeText(
                    mainActivity,
                    "Choose a nickname for this address",
                    Toast.LENGTH_SHORT
                ).show()
                return false
            }

//            else if (addressType.equals("Other", true) && etOtherType.text.toString().isEmpty()) {
//                Toast.makeText(
//                    mainActivity,
//                    "Enter a nickname for this address",
//                    Toast.LENGTH_SHORT
//                ).show()
//                return false
//            }
            else {
                return true
            }
        }
    }


    private fun addAddress(lat:String, long:String, houseno:String, apartmentname:String, areadetails:String,
    streetdetails:String, city:String, pincode:String, addressnickname:String, view: View) {


        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.saveaddress(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob", AddAddressRequestModel(houseNo = houseno, apartmentName = apartmentname, streetDetails = streetdetails,
                    areaDetails = areadetails, city = city, pinCode = pincode, addressNickName = addressnickname,
                    latitude = lat, longitude = long)).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        com.dhobi.perfectdhobi.utils.Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            if (resource.data?.status == true) {

                                val builder = android.app.AlertDialog.Builder(mainActivity)
                                builder.setMessage(resource.data.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.dismiss()
                                    val navController = Navigation.findNavController(view)
                                    navController.navigate(R.id.nav_address)
                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()

                            } else {
                                Toast.makeText(mainActivity, resource.data?.message, Toast.LENGTH_SHORT).show()
                            }

                        }
                        com.dhobi.perfectdhobi.utils.Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            val builder = android.app.AlertDialog.Builder(mainActivity)
                            builder.setMessage(it.data?.message)
                            builder.setPositiveButton(
                                "Ok"
                            ) { dialog, which ->

                                dialog.cancel()

                            }
                            val alert = builder.create()
                            alert.setOnShowListener { arg0 ->
                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(resources.getColor(R.color.blue))
                            }
                            alert.show()


                        }

                        com.dhobi.perfectdhobi.utils.Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }
                    }
                }
            }

        } else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }

    }


    private fun updateAddress(view: View){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.updateaddress(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob", UpdateAddressRequestModel(addressId = addressid,
                    houseNo = fragmentAddAddressBinding.etHouseNo.text.toString(),
                    apartmentName = fragmentAddAddressBinding.etApartmentName.text.toString(),
                    streetDetails = fragmentAddAddressBinding.etStreeDetails.text.toString(),
                    areaDetails = fragmentAddAddressBinding.etAreaDetails.text.toString(),
                    city = fragmentAddAddressBinding.etCity.text.toString(),
                    pinCode = fragmentAddAddressBinding.etPinCode.text.toString(),
                    addressNickName = fragmentAddAddressBinding.etOtherType.text.toString(),
                    latitude = latitude.toString(), longitude = longitude.toString())).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        com.dhobi.perfectdhobi.utils.Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            if (resource.data?.status == true) {

                                val builder = android.app.AlertDialog.Builder(mainActivity)
                                builder.setMessage(resource.data.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.dismiss()
                                    val navController = Navigation.findNavController(view)
                                    navController.navigate(R.id.nav_address)
                                }
                                val alert = builder.create()
                                alert.setOnShowListener { arg0 ->
                                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                        .setTextColor(resources.getColor(R.color.blue))
                                }
                                alert.show()

                            } else {
                                Toast.makeText(mainActivity, resource.data?.message, Toast.LENGTH_SHORT).show()
                            }

                        }
                        com.dhobi.perfectdhobi.utils.Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            val builder = android.app.AlertDialog.Builder(mainActivity)
                            builder.setMessage(it.data?.message)
                            builder.setPositiveButton(
                                "Ok"
                            ) { dialog, which ->

                                dialog.cancel()

                            }
                            val alert = builder.create()
                            alert.setOnShowListener { arg0 ->
                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(resources.getColor(R.color.blue))
                            }
                            alert.show()


                        }

                        com.dhobi.perfectdhobi.utils.Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }
                    }
                }
            }

        } else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }
    }



    private fun editAddress(){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.addressdetails(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob", EditAddressRequestModel(addressId = addressid)
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        com.dhobi.perfectdhobi.utils.Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            if (resource.data?.status == true) {

                                fragmentAddAddressBinding.etHouseNo.setText(resource.data.data.address.houseNo)
                                fragmentAddAddressBinding.etApartmentName.setText(resource.data.data.address.apartmentName)
                                fragmentAddAddressBinding.etStreeDetails.setText(resource.data.data.address.streetDetails)
                                fragmentAddAddressBinding.etAreaDetails.setText(resource.data.data.address.areaDetails)
                                fragmentAddAddressBinding.etCity.setText(resource.data.data.address.city)
                                fragmentAddAddressBinding.etPinCode.setText(resource.data.data.address.pinCode.toString())
                                fragmentAddAddressBinding.etOtherType.setText(resource.data.data.address.addressNickName)

                                if (resource.data.data.address.latitude==null){
                                    latitude = ""
                                }else{
                                    latitude = resource.data.data.address.latitude.toString()
                                }


                                if (resource.data.data.address.longitude==null){
                                    longitude = ""
                                }else{
                                    longitude = resource.data.data.address.longitude.toString()
                                }



                            } else {
                                Toast.makeText(mainActivity, resource.data?.message, Toast.LENGTH_SHORT).show()
                            }

                        }
                        com.dhobi.perfectdhobi.utils.Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            val builder = android.app.AlertDialog.Builder(mainActivity)
                            builder.setMessage(it.data?.message)
                            builder.setPositiveButton(
                                "Ok"
                            ) { dialog, which ->

                                dialog.cancel()

                            }
                            val alert = builder.create()
                            alert.setOnShowListener { arg0 ->
                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                    .setTextColor(resources.getColor(R.color.blue))
                            }
                            alert.show()


                        }

                        com.dhobi.perfectdhobi.utils.Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }
                    }
                }
            }

        } else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }

    }

}