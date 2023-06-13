package com.dhobi.perfectdhobi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.transcationmodel.TranscationModel
import kotlin.collections.ArrayList

class TranscationAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TranscationAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var transactionListModelArrayList: ArrayList<TranscationModel> = arrayListOf()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, id: Int, s: String, mTransactionListModelArrayList: ArrayList<TranscationModel>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_transactionitem, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {



//        holder.tvTitle.text = notificationListModelArrayList[position].time
//        holder.tvDescriptions.text = notificationListModelArrayList[position].description

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

            val navController = Navigation.findNavController(it)
            navController.navigate(R.id.nav_transcationsdetails)


        }


    }




    fun updateData(mTransactionListModelArrayList: List<TranscationModel>){
        transactionListModelArrayList= mTransactionListModelArrayList as ArrayList<TranscationModel>
        notifyDataSetChanged()
    }



    override fun getItemCount(): Int {
        return transactionListModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tvTitle: TextView
        var tvDate: TextView
        var tvPrice: TextView

        init {
            tvTitle = itemView.findViewById(R.id.tvTitle)
            tvDate = itemView.findViewById(R.id.tvDate)
            tvPrice = itemView.findViewById(R.id.tvPrice)
        }
    }
}