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
import com.dhobi.perfectdhobi.data.model.review.ReviewModel


class ReviewAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<ReviewAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var reviewModelArrayList: ArrayList<ReviewModel> = ArrayList()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(
            position: Int,
            view: View,
            mReviewModelArrayList: ArrayList<ReviewModel>
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_reviewitem, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {


            tvDate.text = reviewModelArrayList[position].date
            tvName.text = reviewModelArrayList[position].username
            tvComments.text = reviewModelArrayList[position].comments


        }
    }

    fun updateData(mReviewModelArrayList: List<ReviewModel>) {
        reviewModelArrayList = mReviewModelArrayList as ArrayList<ReviewModel>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return reviewModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvDate: TextView
        var tvName: TextView
        var tvComments: TextView

        init {

            tvDate = itemView.findViewById(R.id.tvDate)
            tvName = itemView.findViewById(R.id.tvName)
            tvComments = itemView.findViewById(R.id.tvComments)
        }
    }
}