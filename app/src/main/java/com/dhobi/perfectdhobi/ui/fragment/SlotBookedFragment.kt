package com.dhobi.perfectdhobi.ui.fragment

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentSlotBookedBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.BookedSlotAdapter
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class SlotBookedFragment : Fragment(),BookedSlotAdapter.OnItemClickListener {
    lateinit var mainActivity: MainActivity
    lateinit var fragmentSlotBookedBinding: FragmentSlotBookedBinding
    private var bookedSlotAdapter: BookedSlotAdapter?=null
    private lateinit var viewModel: CommonViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentSlotBookedBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_slot_booked, container, false)
        val root = fragmentSlotBookedBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentSlotBookedBinding.topnav.tvNavtitle.text = "Back"
        fragmentSlotBookedBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        bookedSlotAdapter = BookedSlotAdapter(mainActivity, this@SlotBookedFragment, false)
        fragmentSlotBookedBinding.rvSlots.adapter = bookedSlotAdapter
        fragmentSlotBookedBinding.rvSlots.layoutManager = GridLayoutManager(mainActivity, 1)
        bookedslot()

    }





    private fun bookedslot(){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.bookedslots(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob")
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {

                                        bookedSlotAdapter?.updateData(itResponse.data.bookedSlots)

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
                                    Toast.makeText(mainActivity, "No Booked Slot found", Toast.LENGTH_SHORT).show()

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

    override fun onClick(
        position: Int,
        view: View,
        mBookedslotModelArrayList: ArrayList<BookedSlot>
    ) {

    }


}