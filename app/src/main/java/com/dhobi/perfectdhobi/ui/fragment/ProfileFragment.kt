package com.dhobi.perfectdhobi.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.ContentValues
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.ProfileUpdateModel.ProfileUpdateRequestModel
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentProfileBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.OTP
import com.dhobi.perfectdhobi.utils.GetRealPathFromUri
import com.dhobi.perfectdhobi.utils.Shared_Preferences
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import com.squareup.picasso.Picasso
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class ProfileFragment : Fragment() {

    lateinit var fragmentProfileBinding: FragmentProfileBinding
    lateinit var mainActivity: MainActivity
    var myCalendar = Calendar.getInstance()
    var selectdate: String? = ""
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentProfileBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false)
        val root = fragmentProfileBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentProfileBinding.topnav.tvNavtitle.text = "Back"

        val selectdate =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                myCalendar[Calendar.YEAR] = year
                myCalendar[Calendar.MONTH] = monthOfYear
                myCalendar[Calendar.DAY_OF_MONTH] = dayOfMonth
                dateupdateLabel(myCalendar)
            }

        fragmentProfileBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        fragmentProfileBinding.imgPrf.setOnClickListener {

            if (ContextCompat.checkSelfPermission(
                    mainActivity,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(mainActivity,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(
                    arrayOf(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    )
                )
            } else {
                ImagePicker.Companion.with(this)
                    .crop()
                    .compress(1024)
                    .maxResultSize(1080, 1080)
                    .start()

            }

        }


        spgender()


        fragmentProfileBinding.btnUpdate.setOnClickListener {

            if (fragmentProfileBinding.etFirstname.text.toString().isEmpty()){
                Toast.makeText(mainActivity, "Enter First Name", Toast.LENGTH_SHORT).show()
            }else if (fragmentProfileBinding.etLastname.text.toString().isEmpty()){
                Toast.makeText(mainActivity, "Enter Last Name", Toast.LENGTH_SHORT).show()
            }else if (fragmentProfileBinding.spGender.getSelectedItem().toString().isEmpty()){
                Toast.makeText(mainActivity, "Select Gender", Toast.LENGTH_SHORT).show()
            }else if (fragmentProfileBinding.etDOB.text.toString().isEmpty()){
                Toast.makeText(mainActivity, "Enter DOB", Toast.LENGTH_SHORT).show()

            }else{

                updateProfile(fragmentProfileBinding.etFirstname.text.toString(),
                    fragmentProfileBinding.etLastname.text.toString(),
                    fragmentProfileBinding.spGender.getSelectedItem().toString(),
                    fragmentProfileBinding.etDOB.text.toString(), it)
            }
        }



        fragmentProfileBinding.etDOB.setOnClickListener {

            val calendar = Calendar.getInstance()
            calendar.add(Calendar.MONTH, 0)
            val datePickerDialog = DatePickerDialog(
                mainActivity, selectdate, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH)
            )
            myCalendar=calendar

            datePickerDialog.setOnShowListener { arg0 ->
                datePickerDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue))
                datePickerDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue))
            }
//            datePickerDialog.datePicker.minDate = calendar.timeInMillis
            datePickerDialog.show()

        }

//        fragmentProfileBinding.spGender.setOnItemSelectedListener(object :
//            AdapterView.OnItemSelectedListener {
//            override fun onItemSelected(
//                parent: AdapterView<*>?,
//                view: View,
//                position: Int,
//                id: Long
//            ) {
//                if (position > 0)
//                    if (fragmentProfileBinding.spGender.getSelectedItem().toString() == "Male") {
//                        selectedgender = "1"
//                    } else if (fragmentProfileBinding.spGender.getSelectedItem().toString() == "Female") {
//                        selectedgender = "2"
//                    } else {
//                        selectedgender = ""
//                    }
//            }
//
//            override fun onNothingSelected(parent: AdapterView<*>?) {}
//        })
        getProfiledetails()

    }


    private fun dateupdateLabel(calendar: Calendar) {
        val selecteddateformat = "dd-MM-yyyy"
        val sdf = SimpleDateFormat(selecteddateformat, Locale.US)
        selectdate = sdf.format(calendar.time)
        fragmentProfileBinding.etDOB.setText(selectdate)

    }


    @SuppressLint("MissingPermission")
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {
            ImagePicker.Companion.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()

        } else {
            // PERMISSION NOT GRANTED
        }
    }



    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        for (fragment in childFragmentManager.fragments) {
            fragment.onActivityResult(requestCode, resultCode, data)
        }

        if (requestCode == 2404 && resultCode == Activity.RESULT_OK) {
            val fileUri = data!!.data

            try {
//                Picasso.get()
//                    .load(fileUri)
//                    .into(fragmentProfileBinding.imgPrf)

                profilepicupdate(GetRealPathFromUri.getPathFromUri(mainActivity, fileUri!!)!!)


            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else if (resultCode == ImagePicker.RESULT_ERROR) {
            Toast.makeText(mainActivity, ImagePicker.RESULT_ERROR, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(mainActivity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }



    private fun spgender() {
        val gender: MutableList<String> = ArrayList()
        gender.add("Male")
        gender.add("Female")
        val arrayAdapter: ArrayAdapter<String> =
            ArrayAdapter<String>(mainActivity, android.R.layout.simple_spinner_item, gender)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        fragmentProfileBinding.spGender.setAdapter(arrayAdapter)
    }



    private fun updateProfile(firstName:String, lastName:String, gender:String, dob:String, view: View){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.updateprofile(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob", ProfileUpdateRequestModel(firstName = firstName, lastName = lastName, gender = gender, dob = dob))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {
                                        val builder = AlertDialog.Builder(mainActivity)
                                        builder.setMessage(resource.data.message)
                                        builder.setPositiveButton(
                                            "Ok"
                                        ) { dialog, which ->

                                            dialog.dismiss()
                                            val navController = Navigation.findNavController(view)
                                            navController.navigate(R.id.nav_profile)
                                        }
                                        val alert = builder.create()
                                        alert.setOnShowListener { arg0 ->
                                            alert.getButton(AlertDialog.BUTTON_POSITIVE)
                                                .setTextColor(resources.getColor(R.color.blue))
                                        }
                                        alert.show()

                                    } else {

                                        Toast.makeText(
                                            mainActivity,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }


                            }
                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                Log.d(ContentValues.TAG, "print-->"+ it.message)
//                                if (it.message!!.contains("404",true)) {
//                                    Toast.makeText(mainActivity, "No data found", Toast.LENGTH_SHORT).show()
//                                }else
                                    Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()


                            }

                            Status.LOADING -> {
                                mainActivity.showProgressDialog()
                            }

                        }

                    }
                }

        } else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()
        }

    }



    private fun getProfiledetails(){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.userprofile(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob")
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {

                                        fragmentProfileBinding.etFirstname.setText(itResponse.data.userData.firstName?:"")
                                        fragmentProfileBinding.etLastname.setText(itResponse.data.userData.lastName?:"")
                                        if (itResponse.data.userData.gender.equals("Male")){
                                            fragmentProfileBinding.spGender.setSelection(0)
                                        }else if (itResponse.data.userData.gender.equals("Female")){
                                            fragmentProfileBinding.spGender.setSelection(1)
                                        }else if (itResponse.data.userData.gender.equals(null)){
                                            fragmentProfileBinding.spGender.setSelection(0)
                                        }else{
                                            fragmentProfileBinding.spGender.setSelection(0)

                                        }



                                        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                                        val output = SimpleDateFormat("dd-MM-yyyy")
                                        var d: Date? = null
                                        try {
                                            d = input.parse(itResponse.data.userData.dob?:"")
                                        } catch (e: ParseException) {
                                            e.printStackTrace()
                                        }
                                        val formatted: String = output.format(d)

                                        fragmentProfileBinding.etDOB.setText(formatted)
                                        Picasso.get()
                                            .load(itResponse.data.userData.profileImage.toString())
                                            .error(R.drawable.dp)
                                            .placeholder(R.drawable.dp)
                                            .into(fragmentProfileBinding.imgPrf)



                                    } else {

                                        Toast.makeText(
                                            mainActivity,
                                            resource.data?.message,
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }


                            }
                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                Log.d(ContentValues.TAG, "print-->"+ it.message)
                                if (it.message!!.contains("404",true)) {
                                    Toast.makeText(mainActivity, "No data found", Toast.LENGTH_SHORT).show()
                                }else
                                    Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()


                            }

                            Status.LOADING -> {
                                mainActivity.showProgressDialog()
                            }

                        }

                    }
                }

        } else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                .show()
        }

    }



    private fun profilepicupdate(fileuri:String){

        if (Utilities.isNetworkAvailable(mainActivity)) {

            val file = File(fileuri)
            val fileReqBody = RequestBody.create("image/jpg".toMediaTypeOrNull(), file)
            val part: MultipartBody.Part =
                MultipartBody.Part.createFormData("image", file.name, fileReqBody)


            viewModel.profilepicupdate(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob", part = part).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            resource.data?.let {itResponse->

                                if (itResponse.status) {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                    Picasso.get()
                                        .load(itResponse.data.profileImage)
                                        .into(fragmentProfileBinding.imgPrf)

                                } else {

                                    Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                }
                            }


                        }
                        Status.ERROR -> {
                            mainActivity.hideProgressDialog()
                            Log.d(ContentValues.TAG, "print-->"+ it.message)
//                                if (it.message!!.contains("404",true)) {
//                                    Toast.makeText(mainActivity, "No data found", Toast.LENGTH_SHORT).show()
//                                }else
                            Toast.makeText(mainActivity, it.message, Toast.LENGTH_SHORT).show()
                        }

                        Status.LOADING -> {
                            mainActivity.showProgressDialog()
                        }

                    }

                }
            }


        }else {
            Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT).show()
        }
    }

}