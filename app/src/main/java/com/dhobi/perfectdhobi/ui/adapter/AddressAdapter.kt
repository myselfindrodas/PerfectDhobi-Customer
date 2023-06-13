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
import com.dhobi.perfectdhobi.data.model.address.Addresse
import com.dhobi.perfectdhobi.utils.Shared_Preferences


class AddressAdapter(
    ctx: Context,
    var onItemClickListener: OnItemClickListener
) :
    RecyclerView.Adapter<AddressAdapter.MyViewHolder>() {
    private val inflater: LayoutInflater
    private var addressModelArrayList: ArrayList<Addresse> = ArrayList()
    var ctx: Context

    init {
        inflater = LayoutInflater.from(ctx)
        this.ctx = ctx
    }


    interface OnItemClickListener {
        fun onClick(
            position: Int,
            view: View,
            mAddressModelArrayList: ArrayList<Addresse>,
            isDelete: Boolean = false
        )
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = inflater.inflate(R.layout.rv_address, parent, false)
        return MyViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        with(holder) {


            tvaddressTitle.text = addressModelArrayList[position].addressNickName
            tvaddressfull.text = addressModelArrayList[position].apartmentName + "," +
                    addressModelArrayList[position].houseNo + "," +
                    addressModelArrayList[position].streetDetails + "," +
                    addressModelArrayList[position].areaDetails + "," +
                    addressModelArrayList[position].city + "," + addressModelArrayList[position].pinCode

            rbCard.isChecked = addressModelArrayList[position].isPrimary == true

            if (addressModelArrayList[position].isPrimary){

                Shared_Preferences.setselectedLat(addressModelArrayList[position].latitude.toString())
                Shared_Preferences.setselectedLon(addressModelArrayList[position].longitude.toString())
                Shared_Preferences.setselectedAddress(addressModelArrayList[position].streetDetails + "," +
                            addressModelArrayList[position].areaDetails)
            }

            itemView.rootView.setOnClickListener {
                onItemClickListener.onClick(position, it, addressModelArrayList, false)

            }

            btnDelete.setOnClickListener {

                onItemClickListener.onClick(position, it, addressModelArrayList, true)
            }

            btnhomeAddaddress.setOnClickListener {
                val bundle = Bundle()
                bundle.putString("viewtype", "edit")
                bundle.putString("addressid", addressModelArrayList[position].id)
                val navController = Navigation.findNavController(it)
                navController.navigate(R.id.nav_add_address, bundle)
            }


        }
    }

    fun updateData(mAddressModelArrayList: List<Addresse>) {
        addressModelArrayList = mAddressModelArrayList as ArrayList<Addresse>
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return addressModelArrayList.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var tvaddressTitle: TextView
        var tvaddressfull: TextView
        var rbCard: RadioButton
        var btnhomeAddaddress: LinearLayout
        var btnDelete: LinearLayout
        var count = 0

        init {

            tvaddressTitle = itemView.findViewById(R.id.tvaddressTitle)
            rbCard = itemView.findViewById(R.id.rbHome)
            tvaddressfull = itemView.findViewById(R.id.tvaddressfull)
            btnhomeAddaddress = itemView.findViewById(R.id.btnhomeAddaddress)
            btnDelete = itemView.findViewById(R.id.btnDelete)
        }
    }
}