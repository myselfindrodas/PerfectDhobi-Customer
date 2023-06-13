package com.dhobi.perfectdhobi.ui.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.Children
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.Menu


class OrderItemAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener) :
    RecyclerView.Adapter<OrderItemAdapter.MyViewHolder>(),OrderSubItemAdapter.OnSubItemClickListener {
    private val inflater: LayoutInflater
    private var orderitemModelArrayList: ArrayList<Menu> = ArrayList()
    private var orderitemSelectedArrayList: ArrayList<Menu> = arrayListOf()
    var ctx: Context
    private var orderSubItemAdapter:OrderSubItemAdapter?=null

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(
            position: Int,
            view: View,
            mOrderitemModelArrayList: ArrayList<Menu>
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_orderitem, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {


            orderitemSelectedArrayList = orderitemModelArrayList
            tvItemcount.text = orderitemModelArrayList[position].children.size.toString()
            tvName.text = orderitemModelArrayList[position].name


            llPress.setOnClickListener {
                if (llSubPress.isVisible) {
                    llSubPress.visibility = View.GONE
                    ivDown.rotation = 90f
                } else {
                    llSubPress.visibility = View.VISIBLE
                    ivDown.rotation = 270f
                }
            }
//            if (orderitemModelArrayList[position].isExpanded){
//                llSubPress.visibility = View.VISIBLE
//                ivDown.rotation = 90f
//
//            }else{
//                llSubPress.visibility = View.GONE
//                ivDown.rotation = 270f
//            }
//
//
//            llPress.setOnClickListener {
//                orderitemModelArrayList.forEach {item->
//                    item.isExpanded=false
//                }
//                orderitemModelArrayList[position].isExpanded=true
//                notifyDataSetChanged()
//            }


            orderSubItemAdapter = OrderSubItemAdapter(ctx, this@OrderItemAdapter)
            rvItemcategory.adapter = orderSubItemAdapter
            rvItemcategory.layoutManager = GridLayoutManager(ctx, 1)
            orderSubItemAdapter?.updateData(orderitemModelArrayList[position].children,position)



        }
    }

    fun updateData(mOrderitemModelArrayList: List<Menu>) {
        orderitemModelArrayList = mOrderitemModelArrayList as ArrayList<Menu>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orderitemModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvItemcount: TextView
        var tvName: TextView
        var ImgItem: ImageView
        var ivDown: ImageView
        var llPress: LinearLayout
        var llSubPress: LinearLayout
        var rvItemcategory: RecyclerView


        init {

            tvItemcount = itemView.findViewById(R.id.tvItemcount)
            tvName = itemView.findViewById(R.id.tvName)
            ImgItem = itemView.findViewById(R.id.ImgItem)
            llPress = itemView.findViewById(R.id.llPress)
            llSubPress = itemView.findViewById(R.id.llSubPress)
            ivDown = itemView.findViewById(R.id.ivDown)
            rvItemcategory = itemView.findViewById(R.id.rvItemcategory)
        }
    }

    fun getSelectedOrder(): ArrayList<Menu>{
        return orderitemSelectedArrayList
    }
    override fun onSubItemClick(
        position: Int,
        view: View,
        itemselectedArrayList1: ArrayList<Children>,
        parentPosition: Int
    ) {
        orderitemSelectedArrayList[parentPosition].children=itemselectedArrayList1

        onItemClickListener.onClick(parentPosition,view,orderitemModelArrayList)

        Log.d(ContentValues.TAG, "OrderitemArray-->$orderitemSelectedArrayList")

    }
}