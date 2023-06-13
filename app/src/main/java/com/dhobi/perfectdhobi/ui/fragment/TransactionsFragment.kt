package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.mycouponmodel.CouponModel
import com.dhobi.perfectdhobi.data.model.transcationmodel.TranscationModel
import com.dhobi.perfectdhobi.databinding.FragmentTransactionsBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.MyCouponAdapter
import com.dhobi.perfectdhobi.ui.adapter.TranscationAdapter

class TransactionsFragment : Fragment(), TranscationAdapter.OnItemClickListener {

    lateinit var fragmentTransactionsBinding: FragmentTransactionsBinding
    lateinit var mainActivity: MainActivity
    private var transcationAdapter:TranscationAdapter?=null
    private val transactionListModelArrayList: ArrayList<TranscationModel> = ArrayList()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentTransactionsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_transactions, container, false)
        val root = fragmentTransactionsBinding.root
        mainActivity = activity as MainActivity

        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentTransactionsBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()

        }

        fragmentTransactionsBinding.topnav.tvNavtitle.text = "Back"


        for (i in 0..5) {

            transactionListModelArrayList.add(
                TranscationModel(
                    "Perfect Dhobi",
                    "16-04-2023 | 16:38:56",
                    "₹ 56"
                )
            )

        }


        transcationAdapter = TranscationAdapter(mainActivity, this@TransactionsFragment)
        fragmentTransactionsBinding.rvTransactionslist.adapter = transcationAdapter
        fragmentTransactionsBinding.rvTransactionslist.layoutManager = GridLayoutManager(mainActivity, 1)

        transcationAdapter?.updateData(transactionListModelArrayList)
    }

    override fun onClick(
        position: Int,
        view: View,
        id: Int,
        s: String,
        mTransactionListModelArrayList: ArrayList<TranscationModel>
    ) {



    }

}