package com.dhobi.perfectdhobi.ui.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.DeleteAddressModel.DeleteAddressRequestModel
import com.dhobi.perfectdhobi.data.model.PrimaryAddressSetModel.PrimaryAddressRequestModel
import com.dhobi.perfectdhobi.data.model.address.Addresse
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentAddressBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.AddressAdapter
import com.dhobi.perfectdhobi.utils.Shared_Preferences
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class AddressFragment : Fragment(), AddressAdapter.OnItemClickListener {

    lateinit var mainActivity: MainActivity
    lateinit var fragmentAddressBinding: FragmentAddressBinding
    private var addressAdapter: AddressAdapter? = null
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentAddressBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_address, container, false)
        val root = fragmentAddressBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm
        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(fragmentAddressBinding) {
            topnav.tvNavtitle.text = "Back"


            addressAdapter = AddressAdapter(mainActivity, this@AddressFragment)
            rvAddress.adapter = addressAdapter
            rvAddress.layoutManager = GridLayoutManager(mainActivity, 1)

            topnav.ivBack.setOnClickListener {

                mainActivity.onBackPressedDispatcher.onBackPressed()
            }


            btnAddaddress.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_add_address)
            }


        }


         getAddressList()
    }

    override fun onClick(
        position: Int,
        view: View,
        mAddressModelArrayList: ArrayList<Addresse>,
        isDelete: Boolean
    ) {
        if (isDelete){
            deleteDialog(mAddressModelArrayList[position])
        }else{

            primaryAddress(mAddressModelArrayList[position])

        }

    }



    private fun deleteDialog(mAddressData: Addresse) {
        val builder = AlertDialog.Builder(mainActivity)
        builder.setTitle("Delete ${mAddressData.addressNickName}")
        builder.setMessage("Are you sure you delete ${mAddressData.addressNickName} from the list?")
        builder.setPositiveButton(
            "Yes"
        ) { dialog, which ->

            deleteAddress(mAddressData.id)
            dialog.dismiss()
        }
        builder.setNegativeButton(
            "Cancel"
        ) { dialog, which ->

            dialog.dismiss()
        }
        val alert = builder.create()
        alert.setOnShowListener { arg0 ->
            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                .setTextColor(resources.getColor(R.color.blue))
            alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(resources.getColor(R.color.blue))
        }
        alert.show()
    }


    private fun getAddressList() {


        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.addresslist(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob")
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {

                                        addressAdapter?.updateData(itResponse.data.addresses)


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
                                    val builder = android.app.AlertDialog.Builder(mainActivity)
                                    builder.setMessage("No Address Found")
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        dialog.cancel()
                                        Shared_Preferences.setselectedAddress("")
                                        Shared_Preferences.setselectedLat("")
                                        Shared_Preferences.setselectedLon("")

                                    }
                                    val alert = builder.create()
                                    alert.setOnShowListener { arg0 ->
                                        alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                            .setTextColor(resources.getColor(R.color.blue))
                                    }
                                    alert.show()
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

    private fun deleteAddress(id:String) {


        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.deleteaddress(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob", DeleteAddressRequestModel(addressId = id))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {
                                        val builder = android.app.AlertDialog.Builder(mainActivity)
                                        builder.setMessage(itResponse.message)
                                        builder.setPositiveButton(
                                            "Ok"
                                        ) { dialog, which ->
                                            getAddressList()
                                            dialog.dismiss()
                                        }
                                        val alert = builder.create()
                                        alert.setOnShowListener { arg0 ->
                                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
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
                                val builder = AlertDialog.Builder(mainActivity)
                                builder.setMessage(it.message)
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

    private fun primaryAddress(mAddressData: Addresse) {


        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.primaryaddress(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob", PrimaryAddressRequestModel(addressId = mAddressData.id))
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {
                                        getAddressList()
                                        val builder = android.app.AlertDialog.Builder(mainActivity)
                                        builder.setMessage(itResponse.message)
                                        builder.setPositiveButton(
                                            "Ok"
                                        ) { dialog, which ->

                                            dialog.dismiss()
                                        }
                                        val alert = builder.create()
                                        alert.setOnShowListener { arg0 ->
                                            alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                                .setTextColor(resources.getColor(R.color.blue))
                                        }
                                        alert.show()

                                    } else {

                                        Toast.makeText(mainActivity, resource.data.message, Toast.LENGTH_SHORT).show()

                                    }
                                }


                            }
                            Status.ERROR -> {
                                mainActivity.hideProgressDialog()
                                val builder = AlertDialog.Builder(mainActivity)
                                builder.setMessage(it.message)
                                builder.setPositiveButton(
                                    "Ok"
                                ) { dialog, which ->

                                    dialog.cancel()

                                }
                                val alert = builder.create()
                                alert.show()
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


}