package com.dhobi.perfectdhobi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.address.Addres
import com.dhobi.perfectdhobi.data.model.myordermodel.MyorderModel


class MyOrderAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MyOrderAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var myorderModelArrayList: ArrayList<MyorderModel> = ArrayList()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(
            position: Int,
            view: View,
            mOrderModelArrayList: ArrayList<MyorderModel>
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_myordersitem, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {


            tvDeliverydate.text = myorderModelArrayList[position].date
            tvAmount.text = myorderModelArrayList[position].orderamount
            tvOrderid.text = myorderModelArrayList[position].orderid

            llDeliveryDetails.setOnClickListener {

                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_orderdetails)
            }


        }
    }

    fun updateData( mOrderModelArrayList: List<MyorderModel>) {
        myorderModelArrayList = mOrderModelArrayList as ArrayList<MyorderModel>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return myorderModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvDeliverydate: TextView
        var tvOrderid: TextView
        var tvAmount: TextView
        var llDeliveryDetails: LinearLayout


        init {

            tvDeliverydate = itemView.findViewById(R.id.tvDeliverydate)
            tvOrderid = itemView.findViewById(R.id.tvOrderid)
            tvAmount = itemView.findViewById(R.id.tvAmount)
            llDeliveryDetails = itemView.findViewById(R.id.llDeliveryDetails)
        }
    }
}