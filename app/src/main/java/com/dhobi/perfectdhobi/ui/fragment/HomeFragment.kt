package com.dhobi.perfectdhobi.ui.fragment

import android.content.ContentValues
import android.content.DialogInterface
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.GravityCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentHomeBinding
import com.dhobi.perfectdhobi.ui.Login
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.BannerAdapter
import com.dhobi.perfectdhobi.ui.adapter.BookedSlotAdapter
import com.dhobi.perfectdhobi.utils.Shared_Preferences
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel
import java.util.*
import kotlin.collections.ArrayList


class HomeFragment : Fragment(),BookedSlotAdapter.OnItemClickListener {

    lateinit var fragmentHomeBinding: FragmentHomeBinding
    lateinit var mainActivity: MainActivity
    lateinit var btnExpandaccount: LinearLayout
    lateinit var llmyaccountsubmenu: LinearLayout
    lateinit var btnNavwallet: LinearLayout
    lateinit var btnNavsaveaddress: LinearLayout
    lateinit var btnNavreview: LinearLayout
    lateinit var btnNavprofile: LinearLayout
    lateinit var btnNavorders: LinearLayout
    lateinit var btnNavRateChart: LinearLayout
    lateinit var btnNavsupport: LinearLayout
    lateinit var btnNavfaq: LinearLayout
    lateinit var btnNavnotification: LinearLayout
    lateinit var btnNavcoupons: LinearLayout
    lateinit var btnNavTranscation: LinearLayout
    lateinit var btnNavEarn: LinearLayout
    lateinit var btnNavlogout: LinearLayout
    lateinit var btnMyaccountExpand: ImageView
    lateinit var btnMyaccountClose: ImageView
    private lateinit var viewModel: CommonViewModel
    lateinit var bannerAdapter: BannerAdapter
    lateinit var bannerAdapterbottom: BannerAdapter
    private val headerHandler = Handler()
    var currentPage = 0
    var delay = 2000
    var runnable: Runnable? = null
    private var bookedSlotAdapter:BookedSlotAdapter?=null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentHomeBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        val root = fragmentHomeBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        btnExpandaccount = root.findViewById(R.id.btnExpandaccount)
        llmyaccountsubmenu = root.findViewById(R.id.llmyaccountsubmenu)
        btnMyaccountClose = root.findViewById(R.id.btnMyaccountClose)
        btnMyaccountExpand = root.findViewById(R.id.btnMyaccountExpand)
        btnNavwallet = root.findViewById(R.id.btnNavwallet)
        btnNavreview = root.findViewById(R.id.btnNavreview)
        btnNavorders = root.findViewById(R.id.btnNavorders)
        btnNavsaveaddress = root.findViewById(R.id.btnNavsaveaddress)
        btnNavprofile = root.findViewById(R.id.btnNavprofile)
        btnNavRateChart = root.findViewById(R.id.btnNavRateChart)
        btnNavsupport = root.findViewById(R.id.btnNavsupport)
        btnNavfaq = root.findViewById(R.id.btnNavfaq)
        btnNavcoupons = root.findViewById(R.id.btnNavcoupons)
        btnNavTranscation = root.findViewById(R.id.btnNavTranscation)
        btnNavnotification = root.findViewById(R.id.btnNavnotification)
        btnNavEarn = root.findViewById(R.id.btnNavEarn)
        btnNavlogout = root.findViewById(R.id.btnNavlogout)

        fragmentHomeBinding.topnav.iv.setOnClickListener {
            fragmentHomeBinding.dl.openDrawer(GravityCompat.START)
        }
        return root
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        llmyaccountsubmenu.visibility = View.GONE
        btnMyaccountClose.visibility = View.GONE
        btnMyaccountExpand.visibility = View.VISIBLE
        fragmentHomeBinding.tvAddress.text = Shared_Preferences.getselectedAddress()
        btnExpandaccount.setOnClickListener {
            if (llmyaccountsubmenu.isVisible) {
                llmyaccountsubmenu.visibility = View.GONE
                btnMyaccountClose.visibility = View.GONE
                btnMyaccountExpand.visibility = View.VISIBLE

                btnExpandaccount.background =
                    ContextCompat.getDrawable(mainActivity, R.drawable.right_rounded_blue_bg_btn)


            } else {
                llmyaccountsubmenu.visibility = View.VISIBLE
                btnMyaccountClose.visibility = View.VISIBLE
                btnMyaccountExpand.visibility = View.GONE
                btnExpandaccount.background = ContextCompat.getDrawable(
                    mainActivity,
                    R.drawable.right_rounded_deepblue_bg_btn
                )


            }
        }





        fragmentHomeBinding.topnav.ivNotification.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)
        }

        fragmentHomeBinding.btnWallet.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wallet)
        }

        fragmentHomeBinding.btnRepeatorder.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("bookingslot", "repeatslot")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_slotbook, bundle)

        }

        btnNavsupport.setOnClickListener {
            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_support)

        }


        fragmentHomeBinding.btnViewallslots.setOnClickListener {


            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_viewallslots)
        }


        btnNavTranscation.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_transcations)
        }

        btnNavfaq.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_faq)
        }

        btnNavwallet.setOnClickListener {
            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_wallet)
        }

        btnNavreview.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_review)
        }

        btnNavEarn.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_refferal)
        }

        btnNavcoupons.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_coupon)

        }

        btnNavnotification.setOnClickListener {
            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_notification)
        }

        btnNavRateChart.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_ratechart)
        }


        btnNavsaveaddress.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_address)
        }


        btnNavorders.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_myorder)
        }


        btnNavprofile.setOnClickListener {

            fragmentHomeBinding.dl.closeDrawer(GravityCompat.START)
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_profile)

        }


        btnNavlogout.setOnClickListener {

            val builder = AlertDialog.Builder(mainActivity)
            builder.setMessage("Do you want to logout?")
            builder.setPositiveButton(
                "Ok"
            ) { dialog, which ->
                logout()
                dialog.cancel()
            }

            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            val alert = builder.create()
            alert.setOnShowListener { arg0: DialogInterface? ->
                alert.getButton(AlertDialog.BUTTON_NEGATIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
                alert.getButton(AlertDialog.BUTTON_POSITIVE)
                    .setTextColor(resources.getColor(R.color.blue, resources.newTheme()))
            }
            alert.show()

        }


        fragmentHomeBinding.btnSlotbook.setOnClickListener {

            val bundle = Bundle()
            bundle.putString("bookingslot", "normalslot")
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_slotbook, bundle)
        }

        fragmentHomeBinding.btnChangeLocation.setOnClickListener {

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_location)
            //showBottomDialog()
        }
        fragmentHomeBinding.cvBulkOrder.setOnClickListener {
            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_bulk_order)
        }




        setupRecyclewrView()
        BannerTop()
        bookedslot()


    }


    private fun logout() {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.logout(
                devicetype = "android",
                key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob"
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()
                            if (resource.data?.status == true) {

                                Shared_Preferences.setLoginStatus(false)
                                Shared_Preferences.clearPref()
                                val intent = Intent(mainActivity, Login::class.java)
                                startActivity(intent)
                                mainActivity.finish()

                            } else {
                                Toast.makeText(
                                    mainActivity,
                                    resource.data?.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        }
                        Status.ERROR -> {
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

                                        if (itResponse.data.bookedSlots.isNotEmpty()){
                                            fragmentHomeBinding.btnViewallslots.visibility = View.VISIBLE
                                            fragmentHomeBinding.rvBookedSlot.visibility = View.VISIBLE
                                        }else if (bookedSlotAdapter!!.itemCount==0){
                                            fragmentHomeBinding.btnViewallslots.visibility = View.GONE
                                            fragmentHomeBinding.rvBookedSlot.visibility = View.GONE

                                        }


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
                                    if (bookedSlotAdapter!!.itemCount>0){
                                        fragmentHomeBinding.btnViewallslots.visibility = View.VISIBLE
                                        fragmentHomeBinding.rvBookedSlot.visibility = View.VISIBLE
                                    }else if (bookedSlotAdapter!!.itemCount==0){
                                        fragmentHomeBinding.btnViewallslots.visibility = View.GONE
                                        fragmentHomeBinding.rvBookedSlot.visibility = View.GONE

                                    }

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



    override fun onPause() {
        super.onPause()
    headerHandler.removeCallbacks(runnable!!)
    }


    override fun onStop() {
        super.onStop()
        headerHandler.removeCallbacks(runnable!!)
    }


    override fun onResume() {
        headerHandler.postDelayed(Runnable {
            headerHandler.postDelayed(runnable!!, delay.toLong())
            if (currentPage === bannerAdapter.itemCount+1 - 1) {
                currentPage = 0
            }
            fragmentHomeBinding.rvBannerTop.setCurrentItem(currentPage++, true)

        }.also { runnable = it }, delay.toLong())
        super.onResume()
    }


    private fun setupRecyclewrView() {
        bannerAdapter = BannerAdapter(mainActivity)
        bannerAdapterbottom = BannerAdapter(mainActivity)

        fragmentHomeBinding.rvBannerTop.setAdapter(bannerAdapter)
        fragmentHomeBinding.dotsIndicatorTop.attachTo(fragmentHomeBinding.rvBannerTop)


        fragmentHomeBinding.rvBannerBottom.setAdapter(bannerAdapterbottom)
        fragmentHomeBinding.dotsIndicatorBottom.attachTo(fragmentHomeBinding.rvBannerBottom)



        bookedSlotAdapter = BookedSlotAdapter(mainActivity, this@HomeFragment, true)
        fragmentHomeBinding.rvBookedSlot.adapter = bookedSlotAdapter
        fragmentHomeBinding.rvBookedSlot.layoutManager = GridLayoutManager(mainActivity, 1)

    }



    private fun BannerTop() {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.BannerHomeTop(
                screenName = "HOME_SCREEN",
                type = "banner", devicetype = "android",
                key = "d77d7bd089b6ea50c35aff32c2ff4608", source = "mob"
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.status == true) {


                                bannerAdapter.updateList(resource.data.data.banners)
                                BannerBottom()


                            } else {
                                Toast.makeText(
                                    mainActivity,
                                    resource.data?.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        }
                        Status.ERROR -> {
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


    private fun BannerBottom() {

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.BannerHomeTop(
                screenName = "HOME_SCREEN",
                type = "promotion", devicetype = "android",
                key = "d77d7bd089b6ea50c35aff32c2ff4608", source = "mob"
            ).observe(mainActivity) {
                it?.let { resource ->
                    when (resource.status) {
                        Status.SUCCESS -> {
                            mainActivity.hideProgressDialog()

                            if (resource.data?.status == true) {


                                bannerAdapterbottom.updateList(resource.data.data.banners)

                            } else {
                                Toast.makeText(
                                    mainActivity,
                                    resource.data?.message,
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }

                        }
                        Status.ERROR -> {
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