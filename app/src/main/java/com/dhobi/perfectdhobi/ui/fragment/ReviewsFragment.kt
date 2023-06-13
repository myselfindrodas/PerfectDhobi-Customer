package com.dhobi.perfectdhobi.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.model.review.ReviewModel
import com.dhobi.perfectdhobi.data.model.timeslotmodel.TimeSlotModel
import com.dhobi.perfectdhobi.databinding.FragmentReviewsBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.ReviewAdapter
import com.dhobi.perfectdhobi.ui.adapter.TimeSlotAdapter
import com.google.android.material.bottomsheet.BottomSheetDialog

class ReviewsFragment : Fragment(), ReviewAdapter.OnItemClickListener {

    lateinit var fragmentReviewsBinding: FragmentReviewsBinding
    lateinit var mainActivity: MainActivity
    private var reviewAdapter: ReviewAdapter? = null
    private val reviewModelArrayList: ArrayList<ReviewModel> = ArrayList()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        fragmentReviewsBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_reviews, container, false)
        val root = fragmentReviewsBinding.root
        mainActivity = activity as MainActivity

        fragmentReviewsBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }


        return root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentReviewsBinding.topnav.tvNavtitle.text = "Reviews"

        for (i in 0..5) {

            reviewModelArrayList.add(
                ReviewModel(
                    "Gregory J. Mather",
                    "24 Mar 2023",
                    "Aut haec tibi, Torquate, sunt vituperanda aut patrocinium tatis repudiandum non possit beatam praestare vitam sapientia."
                )
            )

        }



        reviewAdapter = ReviewAdapter(mainActivity, this@ReviewsFragment)
        fragmentReviewsBinding.rvReviewlist.adapter = reviewAdapter
        fragmentReviewsBinding.rvReviewlist.layoutManager = GridLayoutManager(mainActivity, 1)

        reviewAdapter?.updateData(reviewModelArrayList)


        fragmentReviewsBinding.btnWritereview.setOnClickListener {

            showBottomDialog()

        }


    }


    private fun showBottomDialog(){
        val dialog = BottomSheetDialog(mainActivity,R.style.CustomBottomSheetDialog)
        val view=layoutInflater.inflate(R.layout.bottom_dialog_comment_layout,null)
        dialog.setCancelable(true)
        dialog.setContentView(view)
        dialog.show()


    }


    override fun onClick(position: Int, view: View, mReviewModelArrayList: ArrayList<ReviewModel>) {


    }

}