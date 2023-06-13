package com.dhobi.perfectdhobi.ui.adapter

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.Addon


class OrderAddonAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<OrderAddonAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var orderaddonModelArrayList: ArrayList<Addon> = ArrayList()
    private var selectedaddonModelArrayList: ArrayList<Addon> = ArrayList()
    private var selectedaddonModelArrayList1: ArrayList<Addon> = ArrayList()
    var ctx: Context


    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onAddonClick(
            position: Int,
            view: View,
            mOrderaddonModelArrayList: ArrayList<Addon>
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_addonitem, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            selectedaddonModelArrayList= orderaddonModelArrayList

            if (orderaddonModelArrayList[position].quantity.equals("disabled",true)){
                llQty.visibility = View.INVISIBLE
                tvAmount.text =  "₹ "+orderaddonModelArrayList[position].price.numberDecimal.toFloat().toString()
                orderaddonModelArrayList[position].pricetotal = orderaddonModelArrayList[position].price.numberDecimal.toFloat()
            }else {
                llQty.visibility = View.VISIBLE
                orderaddonModelArrayList[position].pricetotal = 0f
              //  tvAmount.text =  "₹ 0.0"

                // tvAmount.visibility = View.VISIBLE
            }

//            tvItemcount.text = orderitemModelArrayList[position].children.size.toString()
            tvItemname.text = orderaddonModelArrayList[position].name
            tvName.text = orderaddonModelArrayList[position].name
            price = orderaddonModelArrayList[position].price.numberDecimal.toFloat()

            Log.d(ContentValues.TAG, "Adons  PRICE-->$price")
            //count=0
            tvCounter.text=count.toString()
            btnAdd.setOnClickListener {
                count++
                tvCounter.text = count.toString()
                quantity = tvCounter.text.toString().toFloat()
                tvAmount.text = "₹ "+quantity * price
                orderaddonModelArrayList[position].pricetotal = quantity * price

                selectedaddonModelArrayList[position] = orderaddonModelArrayList[position]


                selectedaddonModelArrayList1.clear()
                selectedaddonModelArrayList.forEach {
                    if (it.isChecked){
                        if (it.quantity.equals("enabled",true)) {
                            it.item_quantity = tvCounter.text.toString()
                        }else{
                            it.item_quantity=""
                        }
                        selectedaddonModelArrayList1.add(it)

                    }
                }

                onItemClickListener.onAddonClick(position,it,selectedaddonModelArrayList1)

                Log.d(ContentValues.TAG, "Adons  Array-->$selectedaddonModelArrayList1")
            }

            btnSub.setOnClickListener {
                if (count > 0)
                    count--
                tvCounter.text = count.toString()
                quantity = tvCounter.text.toString().toFloat()
                tvAmount.text = "₹ "+quantity * price
                orderaddonModelArrayList[position].pricetotal = quantity * price
                selectedaddonModelArrayList[position] = orderaddonModelArrayList[position]


                selectedaddonModelArrayList1.clear()
                selectedaddonModelArrayList.forEach {
                    if (it.isChecked){
                        if (it.quantity.equals("enabled",true)) {
                            it.item_quantity = tvCounter.text.toString()
                        }else{
                            it.item_quantity=""
                        }
                        selectedaddonModelArrayList1.add(it)

                    }
                }


                onItemClickListener.onAddonClick(position,it,selectedaddonModelArrayList1)
                Log.d(ContentValues.TAG, "Adons  Array-->$selectedaddonModelArrayList1")
            }


            if (orderaddonModelArrayList[position].isExpanded){
                llSubPress.visibility = View.VISIBLE
                ivDown.rotation = 90f

            }else{
                llSubPress.visibility = View.GONE
                ivDown.rotation = 270f
            }

            cbItem.setOnCheckedChangeListener { buttonView, isChecked ->
                if (isChecked){
                    orderaddonModelArrayList[position].isChecked=true
                    //selectedaddonModelArrayList.add(orderaddonModelArrayList[position])
                }else{
                   // selectedaddonModelArrayList.remove(orderaddonModelArrayList[position])
                    orderaddonModelArrayList[position].isChecked=false
                }
                if (orderaddonModelArrayList[position].quantity == "disabled"){

                }
                selectedaddonModelArrayList[position] = orderaddonModelArrayList[position] //something like this?



                selectedaddonModelArrayList1.clear()
                selectedaddonModelArrayList.forEach {
                    if (it.isChecked){
                        if (it.quantity.equals("enabled",true)) {
                            it.item_quantity = tvCounter.text.toString()
                        }else{
                            it.item_quantity=""
                        }
                        selectedaddonModelArrayList1.add(it)

                    }
                }


                onItemClickListener.onAddonClick(position,buttonView,selectedaddonModelArrayList1)
                Log.d(ContentValues.TAG, "Adons  Array-->$selectedaddonModelArrayList1")
                /*itemselectedArrayList1.clear()
                itemselectedArrayList.forEach {
                    if (it.isChecked){
                        itemselectedArrayList1.add(it)

                    }
                }
                Log.d(ContentValues.TAG, "OrderADDONSArray-->$selectedaddonModelArrayList")*/
            }

            llPress.setOnClickListener {
                orderaddonModelArrayList.forEach {item->
                    item.isExpanded=false
                }
                orderaddonModelArrayList[position].isExpanded=true
                notifyDataSetChanged()
            }




        }
    }

    fun getSelectedAddOnList():ArrayList<Addon>{
        return selectedaddonModelArrayList1
    }
    fun updateData(mOrderaddonModelArrayList: List<Addon>) {
        orderaddonModelArrayList = mOrderaddonModelArrayList as ArrayList<Addon>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return orderaddonModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvItemname: TextView
        var tvName: TextView
        var tvAmount: TextView
        var ImgItem: ImageView
        var ivDown: ImageView
        var llPress: LinearLayout
        var llSubPress: LinearLayout
        var llQty: LinearLayout
        var cbItem: CheckBox
        var tvCounter: TextView
        var btnAdd: AppCompatButton
        var btnSub: AppCompatButton
        var count = 0
        var quantity: Float = 0f
        var price: Float = 0f

        init {

            tvItemname = itemView.findViewById(R.id.tvItemname)
            tvName = itemView.findViewById(R.id.tvName)
            tvAmount = itemView.findViewById(R.id.tvAmount)
            ImgItem = itemView.findViewById(R.id.ImgItem)
            llPress = itemView.findViewById(R.id.llPress)
            llSubPress = itemView.findViewById(R.id.llSubPress)
            ivDown = itemView.findViewById(R.id.ivDown)
            llQty = itemView.findViewById(R.id.llQty)
            cbItem = itemView.findViewById(R.id.cbItem)
            tvCounter = itemView.findViewById(R.id.tvCounter)
            btnAdd = itemView.findViewById(R.id.btnAdd)
            btnSub = itemView.findViewById(R.id.btnSub)
        }
    }
}