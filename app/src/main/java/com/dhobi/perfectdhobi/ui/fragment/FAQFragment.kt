package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
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
import com.dhobi.perfectdhobi.data.model.HelpandSupportModel.HelpAndSupport
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.FragmentFAQBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.FAQ_Help_Adapter
import com.dhobi.perfectdhobi.utils.Status
import com.dhobi.perfectdhobi.utils.Utilities
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel

class FAQFragment : Fragment(), FAQ_Help_Adapter.OnItemClickListener {

    lateinit var fragmentFAQBinding: FragmentFAQBinding
    lateinit var mainActivity: MainActivity

    private var faqHelpAdapter: FAQ_Help_Adapter? = null
    private lateinit var viewModel: CommonViewModel

//    private val contentModelArrayList: ArrayList<HelpAndSupport> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentFAQBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_f_a_q, container, false)
        val root = fragmentFAQBinding.root
        mainActivity = activity as MainActivity

        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentFAQBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()

        }

        fragmentFAQBinding.topnav.tvNavtitle.text = "Back"

//        for (i in 0..5) {
//
//            contentModelArrayList.add(
//                HelpAndSupport(
//                    true,
//                    "Receive notification relat to order status, payment and support Aut haec tibi, Torquate, sunt vituperanda aut patrocinium voluptatis repudian dum. Quod si ita se habeat, non possit beatam praestare vitam sapientia.",
//                    "",
//                    "",
//                    true,
//                    false,
//                    "How to Check status of My Order",
//                    ""
//
//                )
//            )
//
//        }


        faqHelpAdapter = FAQ_Help_Adapter(mainActivity, this@FAQFragment,true)
        fragmentFAQBinding.rvFAQ.adapter = faqHelpAdapter
        fragmentFAQBinding.rvFAQ.layoutManager = GridLayoutManager(mainActivity, 1)
        faqList()
//        faqHelpAdapter?.updateData(contentModelArrayList)


    }

    override fun onClick(
        position: Int,
        view: View,
        id: Int,
        s: String,
        mNotificationListModelArrayList: ArrayList<HelpAndSupport>
    ) {

    }


    private fun faqList(){

        if (Utilities.isNetworkAvailable(mainActivity)) {


            viewModel.faq(devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                source = "mob")
                .observe(mainActivity) {
                    it?.let { resource ->
                        when (resource.status) {
                            Status.SUCCESS -> {
                                mainActivity.hideProgressDialog()
                                resource.data?.let {itResponse->

                                    if (itResponse.status) {

                                        faqHelpAdapter?.updateData(itResponse.data.faq as ArrayList<HelpAndSupport>)

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


}