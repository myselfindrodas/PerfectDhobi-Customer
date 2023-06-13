package com.dhobi.perfectdhobi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.RatechartModel.Addon
import com.dhobi.perfectdhobi.data.model.RatechartModel.RateChart
import com.dhobi.perfectdhobi.data.model.ratemodel.RateModel
import kotlin.collections.ArrayList

class AddonRatechartAdapter(
    ctx: Context
) :
    RecyclerView.Adapter<AddonRatechartAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var addonratechartListModelArrayList: ArrayList<Addon> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_rateitem, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



        holder.tvrateDescription.text = addonratechartListModelArrayList[position].name
        holder.tvRate.text = "₹"+addonratechartListModelArrayList[position].price.numberDecimal

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




    fun updateData(mAddonRatechartListModelArrayList: List<Addon>){
        addonratechartListModelArrayList= mAddonRatechartListModelArrayList as ArrayList<Addon>
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return addonratechartListModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvrateDescription: TextView
        var tvRate: TextView

        init {
            tvrateDescription = itemView.findViewById(R.id.tvrateDescription)
            tvRate = itemView.findViewById(R.id.tvRate)
        }
    }
}