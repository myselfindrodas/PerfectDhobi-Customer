package com.dhobi.perfectdhobi.ui.adapter

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.recyclerview.widget.RecyclerView
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.menu_and_addon_model.Children
import org.json.JSONObject


class OrderSubItemAdapter(
    ctx: Context,
    var onItemClickListener: OnSubItemClickListener
) :
    RecyclerView.Adapter<OrderSubItemAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var suborderitemModelArrayList: ArrayList<Children> = ArrayList()
    private var itemselectedArrayList: ArrayList<Children> = ArrayList()
    private var itemselectedArrayList1: ArrayList<Children> = ArrayList()
    var ctx: Context
    var parentPosition: Int = 0
    var objMainList = JSONObject()


    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnSubItemClickListener {
        fun onSubItemClick(
            position: Int,
            view: View,
            itemselectedArrayList1: ArrayList<Children>,
            parentPosition: Int
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_itemcategory, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n", "SuspiciousIndentation")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            itemselectedArrayList = suborderitemModelArrayList //something like this?

            tvItemname.text = suborderitemModelArrayList[position].name
            price = suborderitemModelArrayList[position].price.numberDecimal.toFloat()
            println("SUBLIST PRICE $price")
            count = 0
            tvCounter.text = count.toString()
            btnAdd.setOnClickListener {
                count++
                tvCounter.text = count.toString()
                suborderitemModelArrayList[position].qty = count
                quantity = tvCounter.text.toString().toFloat()
                tvAmount.text = "₹ " + quantity * price
                suborderitemModelArrayList[position].pricetotal = quantity * price
                itemselectedArrayList[position] = suborderitemModelArrayList[position] //something like this?


                itemselectedArrayList1.clear()
                itemselectedArrayList.forEach {
                    if (it.isChecked){
                        itemselectedArrayList1.add(it)

                    }
                }

                onItemClickListener.onSubItemClick(position,it,itemselectedArrayList1,parentPosition)

            }

            btnSub.setOnClickListener {
                if (count > 0)
                    count--
                tvCounter.text = count.toString()
                suborderitemModelArrayList[position].qty = count
                quantity = tvCounter.text.toString().toFloat()
                tvAmount.text = "₹ " + quantity * price
                suborderitemModelArrayList[position].pricetotal = quantity * price

                itemselectedArrayList[position] = suborderitemModelArrayList[position] //something like this?


                itemselectedArrayList1.clear()
                itemselectedArrayList.forEach {
                    if (it.isChecked){
                        itemselectedArrayList1.add(it)

                    }
                }


                onItemClickListener.onSubItemClick(position,it,itemselectedArrayList1,parentPosition)
            }


            cbItem.setOnCheckedChangeListener { buttonView, isChecked ->

                if (isChecked) {
                    suborderitemModelArrayList[position].isChecked = true

                   /* val itemArray = JSONArray()
                    val itemobj = JSONObject()
                    itemobj.put("id", suborderitemModelArrayList[position].id)
                    itemobj.put("item", suborderitemModelArrayList[position].name)
                    itemobj.put("qty", suborderitemModelArrayList[position].qty)
                    itemobj.put("price", suborderitemModelArrayList[position].pricetotal)
                    itemArray.put(itemobj)
                    objMainList.put("items", itemArray)*/
                    //itemselectedArrayList.add(objMainList.toString()) //something like this?
                  //  itemselectedArrayList.add(suborderitemModelArrayList[position]) //something like this?

                } else {
                    suborderitemModelArrayList[position].isChecked = false
                    //itemselectedArrayList.remove(suborderitemModelArrayList[position]) //something like this?

                   /* if( position >= itemselectedArrayList.size){
                        itemselectedArrayList.removeAt(position)
                    }*/

                }
                itemselectedArrayList[position] = suborderitemModelArrayList[position] //something like this?


                itemselectedArrayList1.clear()
                itemselectedArrayList.forEach {
                    if (it.isChecked){
                        itemselectedArrayList1.add(it)

                    }
                }
                Log.d(TAG, "itemArray-->$itemselectedArrayList1")

                onItemClickListener.onSubItemClick(position,buttonView,itemselectedArrayList1,parentPosition)
            }


        }
    }

    fun getfinalList():ArrayList<Children>{
        return itemselectedArrayList1
    }


    fun updateData(mSuborderitemModelArrayList: List<Children>, mParentPosition:Int) {
        parentPosition = mParentPosition
        suborderitemModelArrayList = mSuborderitemModelArrayList as ArrayList<Children>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return suborderitemModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var cbItem: CheckBox
        var tvItemname: TextView

        //        var btnQty: HorizontalQuantitizer
        var tvAmount: TextView
        var tvCounter: TextView
        var btnAdd: AppCompatButton
        var btnSub: AppCompatButton
        var count = 0

        var quantity: Float = 0f
        var price: Float = 0f

        init {

            cbItem = itemView.findViewById(R.id.cbItem)
            tvItemname = itemView.findViewById(R.id.tvItemname)
//            btnQty = itemView.findViewById(R.id.btnQty)
            tvAmount = itemView.findViewById(R.id.tvAmount)
            tvCounter = itemView.findViewById(R.id.tvCounter)
            btnAdd = itemView.findViewById(R.id.btnAdd)
            btnSub = itemView.findViewById(R.id.btnSub)
        }
    }
}