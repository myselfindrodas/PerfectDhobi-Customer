package com.dhobi.perfectdhobi.ui.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.BookedSlotModel.BookedSlotModelResponse.BookedSlot
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


class BookedSlotAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener,
    var isHome: Boolean = true
) :
    RecyclerView.Adapter<BookedSlotAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var bookedslotModelArrayList: ArrayList<BookedSlot> = ArrayList()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(
            position: Int,
            view: View,
            mBookedslotModelArrayList: ArrayList<BookedSlot>
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_bookedslots, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {


            if (bookedslotModelArrayList[position].deliveryDate == null) {

                tvDeliveryDate.text =
                    "Delivery day - " + bookedslotModelArrayList[position].deliveryDayName +
                            "per week" + " Pickup day -" +
                            bookedslotModelArrayList[position].pickUpDayName + "per week"
                tvDeliveryTime.text = bookedslotModelArrayList[position].deliveryTime
                tvName.text = "Indranil"

            } else {
                val input = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
                val output = SimpleDateFormat("dd-MM-yyyy")
                var d: Date? = null
                try {
                    d = input.parse(bookedslotModelArrayList[position].deliveryDate)
                } catch (e: ParseException) {
                    e.printStackTrace()
                }
                val formatted: String = output.format(d)

                tvDeliveryDate.text = formatted
                tvDeliveryTime.text = bookedslotModelArrayList[position].deliveryTime
                tvName.text = "Indranil"
            }




            itemView.rootView.setOnClickListener {
                onItemClickListener.onClick(position, it, bookedslotModelArrayList)

            }


        }
    }

    fun updateData(mBookedslotModelArrayList: List<BookedSlot>) {
        bookedslotModelArrayList = mBookedslotModelArrayList as ArrayList<BookedSlot>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {

        if (bookedslotModelArrayList.isEmpty()) {
            return 0
        } else {
            if (isHome) {
                return 1

            } else {
                return bookedslotModelArrayList.size
            }

        }

    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvDeliveryDate: TextView
        var tvDeliveryTime: TextView
        var tvName: TextView
        var imgDeliveryboy: ImageView

        init {

            tvDeliveryDate = itemView.findViewById(R.id.tvDeliveryDate)
            tvDeliveryTime = itemView.findViewById(R.id.tvDeliveryTime)
            tvName = itemView.findViewById(R.id.tvName)
            imgDeliveryboy = itemView.findViewById(R.id.imgDeliveryboy)
        }
    }
}