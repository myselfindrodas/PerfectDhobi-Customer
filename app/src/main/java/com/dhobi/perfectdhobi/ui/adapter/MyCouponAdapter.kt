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
import com.dhobi.perfectdhobi.data.model.mycouponmodel.CouponModel
import com.dhobi.perfectdhobi.data.model.myordermodel.MyorderModel


class MyCouponAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<MyCouponAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var mycouponModelArrayList: ArrayList<CouponModel> = ArrayList()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(
            position: Int,
            view: View,
            mMycouponModelArrayList: ArrayList<CouponModel>
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_couponsitem, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {


            tvTitle.text = mycouponModelArrayList[position].title
            tvDescription.text = mycouponModelArrayList[position].description
            tvCode.text = mycouponModelArrayList[position].code


        }
    }

    fun updateData( mMycouponModelArrayList: List<CouponModel>) {
        mycouponModelArrayList = mMycouponModelArrayList as ArrayList<CouponModel>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return mycouponModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTitle: TextView
        var tvDescription: TextView
        var tvCode: TextView


        init {

            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDescription = itemView.findViewById(R.id.tvDescription)
            tvCode = itemView.findViewById(R.id.tvCode)
        }
    }
}