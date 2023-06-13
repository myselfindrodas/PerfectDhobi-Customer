package com.dhobi.perfectdhobi.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.timeslotmodel.TimeSlotModel


class TimeSlotAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<TimeSlotAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var timeslotModelArrayList: ArrayList<TimeSlotModel> = arrayListOf()
    var ctx: Context
    var selectedPosition = -1

    init {
        inflater = LayoutInflater.from(ctx)
        // this.savedCardModelArrayList = savedCardModelArrayList
        this.ctx = ctx
    }

    interface OnItemClickListener {
        fun onClick(position: Int, view: View, mTimeslotModelArrayList: ArrayList<TimeSlotModel>)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_timeslot, parent, false)
        return MyViewHolder(view)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {

            holder.tvTime.text = timeslotModelArrayList[position].time
            if (selectedPosition == position) {
                holder.llslotbg.setBackgroundResource(R.drawable.sky_border_circle)
                holder.tvTime.setTextColor(ContextCompat.getColor(ctx,R.color.white))


            } else {
                holder.llslotbg.setBackgroundResource(R.drawable.grey_border_circle)
                holder.tvTime.setTextColor(ContextCompat.getColor(ctx,R.color.grey_text))

            }

            itemView.rootView.setOnClickListener {
                onItemClickListener.onClick(position, it, timeslotModelArrayList)
                selectedPosition=position
                notifyDataSetChanged()

            }


        }
    }

    fun updateData(mTimeSlotModelArrayList: ArrayList<TimeSlotModel>) {
        timeslotModelArrayList = mTimeSlotModelArrayList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return timeslotModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvTime: TextView
        var llslotbg: LinearLayout

        init {

            tvTime = itemView.findViewById(R.id.tvTime)
            llslotbg = itemView.findViewById(R.id.llslotbg)
        }
    }
}