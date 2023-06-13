package com.dhobi.perfectdhobi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.reffermodel.RefferalModel
import kotlin.collections.ArrayList

class RefferalAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<RefferalAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var refferalListModelArrayList: ArrayList<RefferalModel> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, id: Int, s: String, mRefferalListModelArrayList: ArrayList<RefferalModel>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_refferitem, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        holder.tvName.text = refferalListModelArrayList[position].name
        holder.tvPhone.text = refferalListModelArrayList[position].phone

//        val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
//        val output = SimpleDateFormat("dd-MM-yyyy")
//        var d: Date? = null
//        try {
//            d = input.parse(notificationListModelArrayList[position].createdAt.toString())
//        } catch (e: ParseException) {
//            e.printStackTrace()
//        }
//        val formatted: String = output.format(d)


//        holder.tvTitle.text = formatted
//        holder.tvDescriptions.text = notificationListModelArrayList[position].description


        holder.itemView.rootView.setOnClickListener {
//            onItemClickListener.onClick(position, it,notificationListModelArrayList[position].id,
//                notificationListModelArrayList[position].title, notificationListModelArrayList)

        }


    }




    fun updateData(mRefferalListModelArrayList: List<RefferalModel>){
        refferalListModelArrayList= mRefferalListModelArrayList as ArrayList<RefferalModel>
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return refferalListModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvName: TextView
        var tvPhone: TextView

        init {
            tvName = itemView.findViewById(R.id.tvName)
            tvPhone = itemView.findViewById(R.id.tvPhone)
        }
    }
}