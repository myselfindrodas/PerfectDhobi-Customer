package com.dhobi.perfectdhobi.ui.fragment

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.reffermodel.RefferalModel
import com.dhobi.perfectdhobi.databinding.FragmentRefferEarnBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.RefferalAdapter

class RefferEarnFragment : Fragment(), RefferalAdapter.OnItemClickListener {

    lateinit var fragmentRefferEarnBinding: FragmentRefferEarnBinding

    lateinit var mainActivity: MainActivity

    private var refferalAdapter: RefferalAdapter? = null
    private val refferalListModelArrayList: ArrayList<RefferalModel> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentRefferEarnBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reffer_earn, container, false)
        val root = fragmentRefferEarnBinding.root
        mainActivity = activity as MainActivity



        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        fragmentRefferEarnBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()

        }

        fragmentRefferEarnBinding.topnav.tvNavtitle.text = "Back"



        if (ContextCompat.checkSelfPermission(mainActivity, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(arrayOf(Manifest.permission.READ_CONTACTS))
        } else {


            refferalAdapter = RefferalAdapter(mainActivity, this@RefferEarnFragment)
            fragmentRefferEarnBinding.rvRefferlist.adapter = refferalAdapter
            fragmentRefferEarnBinding.rvRefferlist.layoutManager = GridLayoutManager(mainActivity, 1)


            getContactc()
            refferalAdapter?.updateData(refferalListModelArrayList)


        }


    }


    @SuppressLint("Range")
    private fun getContactc() {
        refferalListModelArrayList.clear()
        val cursor = mainActivity.contentResolver
            .query(
                ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                arrayOf(
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER,

                    ),null,null,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
            )
        while (cursor!!.moveToNext()){
            val contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
            val contactNumber = cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
            val refferalModel =  RefferalModel(contactName,contactNumber)
            refferalListModelArrayList.add(refferalModel)
        }
        refferalAdapter?.notifyDataSetChanged()
        cursor.close()
    }



    @SuppressLint("MissingPermission")
    val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val granted = permissions.entries.all {
            it.value
        }
        if (granted) {

            refferalAdapter = RefferalAdapter(mainActivity, this@RefferEarnFragment)
            fragmentRefferEarnBinding.rvRefferlist.adapter = refferalAdapter
            fragmentRefferEarnBinding.rvRefferlist.layoutManager = GridLayoutManager(mainActivity, 1)


            getContactc()
            refferalAdapter?.updateData(refferalListModelArrayList)


        } else {
            // PERMISSION NOT GRANTED
        }


    }


    override fun onClick(
        position: Int,
        view: View,
        id: Int,
        s: String,
        mRefferalListModelArrayList: ArrayList<RefferalModel>
    ) {



    }

}