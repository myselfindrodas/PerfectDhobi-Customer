package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.notificationmodel.Notification
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentNotificationBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.NotificationAdapter
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class NotificationFragment : Fragment(), NotificationAdapter.OnItemClickListener {


    lateinit var fragmentNotificationBinding: FragmentNotificationBinding
    lateinit var mainActivity: MainActivity
    private var notificationAdapter: NotificationAdapter? = null
    private lateinit var viewModel: CommonViewModel

//    private val notificationListModelArrayList: ArrayList<NotifiactionModel>  = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentNotificationBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_notification, container, false)
        val root = fragmentNotificationBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm


        fragmentNotificationBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()

        }



        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentNotificationBinding.topnav.tvNavtitle.text = "Back"

//        for (i in 0..5) {
//
//            notificationListModelArrayList.add(
//                NotifiactionModel(
//                    "23-03-2023",
//                    "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s"
//                )
//            )
//
//        }



        notificationAdapter = NotificationAdapter(mainActivity, this@NotificationFragment)
        fragmentNotificationBinding.rvNotification.adapter = notificationAdapter
        fragmentNotificationBinding.rvNotification.layoutManager = GridLayoutManager(mainActivity, 1)

        notificationList()
//        notificationAdapter?.updateData(notificationListModelArrayList)


    }


    private fun notificationList(){

        if (Utilities.isNetworkAvailable(mainActivity)) {
            viewModel.notificationlist(devicetype = "android",
                key = "d77d7bd089b6ea50c35aff32c2ff4608", source = "mob")
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {
                                        notificationAdapter?.updateData(itResponse.data.notifications)
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
        id: Int,
        s: String,
        mNotificationListModelArrayList: ArrayList<Notification>
    ) {



    }

}