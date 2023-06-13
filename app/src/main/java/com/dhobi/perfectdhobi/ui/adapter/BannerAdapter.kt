package com.dhobi.perfectdhobi.ui.adapter

import android.content.Context
import android.view.*
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.BannerModel.Data

class BannerAdapter(var context: Context) :
    RecyclerView.Adapter<BannerAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    var modelList: MutableList<String> = arrayListOf()
    var ctx: Context
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_banner, parent, false)
        return MyViewHolder(view)
    }


    fun updateList(mModelList: List<String>) {
        modelList.clear()
        modelList.addAll(mModelList)
        notifyDataSetChanged()

    }

    fun addToList(mModelList: List<String>) {
        val lastIndex: Int = modelList.size
        modelList.addAll(mModelList)
        notifyItemRangeInserted(lastIndex, mModelList.size)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Glide.with(context)
            .load(modelList[position])
            .timeout(6000)
            .error(R.drawable.adbg1)
            .placeholder(R.drawable.adbg1)
            .into(holder.ImgBanner)



    }

    override fun getItemCount(): Int {
        return modelList.size
    }


    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var ImgBanner: ImageView

        init {
            ImgBanner = itemView.findViewById(R.id.ImgBanner)

        }
    }

    init {
        inflater = LayoutInflater.from(context)
        this.ctx = context
    }
}