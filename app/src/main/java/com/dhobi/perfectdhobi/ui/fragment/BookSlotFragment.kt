package com.dhobi.perfectdhobi.ui.fragment

import android.app.Dialog
import android.content.ContentValues.TAG
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.GridLayoutManager
import com.dhobi.perfectdhobi.R
import com.dhobi.perfectdhobi.data.ApiClient
import com.dhobi.perfectdhobi.data.ApiHelper
import com.dhobi.perfectdhobi.data.model.SlotBookingModel.SlotBookingRequestModel
import com.dhobi.perfectdhobi.data.model.timeslotmodel.TimeSlotModel
import com.dhobi.perfectdhobi.data.modelfactory.CommonModelFactory
import com.dhobi.perfectdhobi.databinding.Example7CalendarDayBinding
import com.dhobi.perfectdhobi.databinding.FragmentBookSlotBinding
import com.dhobi.perfectdhobi.ui.MainActivity
import com.dhobi.perfectdhobi.ui.adapter.TimeSlotAdapter
import com.dhobi.perfectdhobi.utils.*
import com.dhobi.perfectdhobi.viewmodel.CommonViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.kizitonwose.calendar.core.WeekDay
import com.kizitonwose.calendar.core.atStartOfMonth
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.sample.view.getColorCompat
import com.kizitonwose.calendar.view.ViewContainer
import com.kizitonwose.calendar.view.WeekDayBinder
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.*

class BookSlotFragment : Fragment(), TimeSlotAdapter.OnItemClickListener {

    lateinit var fragmentBookSlotBinding: FragmentBookSlotBinding
    lateinit var mainActivity: MainActivity

    private var timeSlotAdapter: TimeSlotAdapter? = null
    private val morningtimeslotModelArrayList: ArrayList<TimeSlotModel> = arrayListOf()
    private val afternoontimeslotModelArrayList: ArrayList<TimeSlotModel> = arrayListOf()
    private val eveningtimeslotModelArrayList: ArrayList<TimeSlotModel> = arrayListOf()

    @RequiresApi(Build.VERSION_CODES.O)
    private var selectedDate = LocalDate.now()

    @RequiresApi(Build.VERSION_CODES.O)
    private val dateFormatter = DateTimeFormatter.ofPattern("dd")
    var dialog: Dialog? = null

    @RequiresApi(Build.VERSION_CODES.O)
    var currentMonth = YearMonth.now()

    @RequiresApi(Build.VERSION_CODES.O)
    var selectedMonth: String = ""
    var selectedTime = ""
    var bookingtype = ""
    var isDelivery: Boolean = false
    private lateinit var viewModel: CommonViewModel
    var booking = ""
    var bookingOption = ""
    var pickUpDayName = ""
    var deliveryDayName = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        fragmentBookSlotBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_book_slot, container, false)
        val root = fragmentBookSlotBinding.root
        mainActivity = activity as MainActivity


        val vm: CommonViewModel by viewModels {
            CommonModelFactory(ApiHelper(ApiClient.apiService))
        }

        viewModel = vm

        fragmentBookSlotBinding.topnav.ivBack.setOnClickListener {

            mainActivity.onBackPressedDispatcher.onBackPressed()
        }

        return root

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fragmentBookSlotBinding.topnav.tvNavtitle.text = "Book Your Slot"
        Shared_Preferences.setSelectedDeliverydate("")
        Shared_Preferences.setSelectedDeliverytime("")
        Shared_Preferences.setSelectedPickupdate("")
        Shared_Preferences.setSelectedPickuptime("")

        val intent = arguments
        if (intent != null && intent.containsKey("bookingslot")) {
            bookingtype = intent.getString("bookingslot", "")
        }


        if (bookingtype.equals("repeatslot")) {
            fragmentBookSlotBinding.llrecurringslot.visibility = View.VISIBLE
            booking = "recurring"
        } else {

            fragmentBookSlotBinding.llrecurringslot.visibility = View.GONE
            booking = "single"

        }

        //Morning Array
        morningtimeslotModelArrayList.add(TimeSlotModel("6:00 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("6:30 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("7:00 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("7:30 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("8:00 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("8:30 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("9:00 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("9:30 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("10:00 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("10:30 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("11:00 AM"))
        morningtimeslotModelArrayList.add(TimeSlotModel("11:30 AM"))

        //Afternoon Array

        afternoontimeslotModelArrayList.add(TimeSlotModel("12:00 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("12:30 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("1:00 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("1:30 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("2:00 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("2:30 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("3:00 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("3:30 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("4:00 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("4:30 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("5:00 PM"))
        afternoontimeslotModelArrayList.add(TimeSlotModel("5:30 PM"))


        //Evening Array

        eveningtimeslotModelArrayList.add(TimeSlotModel("6:00 PM"))
        eveningtimeslotModelArrayList.add(TimeSlotModel("6:30 PM"))
        eveningtimeslotModelArrayList.add(TimeSlotModel("7:00 PM"))
        eveningtimeslotModelArrayList.add(TimeSlotModel("7:30 PM"))
        eveningtimeslotModelArrayList.add(TimeSlotModel("8:00 PM"))
        eveningtimeslotModelArrayList.add(TimeSlotModel("8:30 PM"))
        eveningtimeslotModelArrayList.add(TimeSlotModel("9:00 PM"))

//        for (i in 0..5) {
//
//            timeslotModelArrayList.add(
//                TimeSlotModel(
//                    "06:00 PM"))
//
//        }


        timeSlotAdapter = TimeSlotAdapter(mainActivity, this@BookSlotFragment)
        fragmentBookSlotBinding.rvTimeslot.adapter = timeSlotAdapter
        fragmentBookSlotBinding.rvTimeslot.layoutManager = GridLayoutManager(mainActivity, 4)

        timeSlotAdapter?.updateData(morningtimeslotModelArrayList)



        @RequiresApi(Build.VERSION_CODES.O)
        class DayViewContainer(view: View) : ViewContainer(view) {
            val bind = Example7CalendarDayBinding.bind(view)
            lateinit var day: WeekDay

            init {

                if (selectedMonth.length == 0) {
                    selectedMonth = LocalDate.now().month.toString()
                }

                Log.d(TAG, "selectedmonth-->" + selectedMonth)


                view.setOnClickListener {
                    if (YearMonth.now().month.toString().equals(selectedMonth)) {
                        if (day.date >= LocalDate.now() && day.date <= YearMonth.now()
                                .atEndOfMonth()
                        ) {
                            if (selectedDate != day.date) {
                                val oldDate = selectedDate
                                selectedDate = day.date
                                fragmentBookSlotBinding.exSevenCalendar.notifyDateChanged(day.date)
                                oldDate?.let {
                                    fragmentBookSlotBinding.exSevenCalendar.notifyDateChanged(
                                        it
                                    )
                                }
                            }
                        }
                    } else {

//                        if (day.date>=YearMonth.now().atStartOfMonth() && day.date<=YearMonth.now().atEndOfMonth()){
                        if (day.date.month.toString().equals(selectedMonth)) {
                            if (selectedDate != day.date) {
                                val oldDate = selectedDate
                                selectedDate = day.date
                                fragmentBookSlotBinding.exSevenCalendar.notifyDateChanged(day.date)
                                try {
                                    oldDate?.let {
                                        fragmentBookSlotBinding.exSevenCalendar.notifyDateChanged(
                                            it
                                        )
                                    }

                                } catch (ex: Exception) {
                                    ex.printStackTrace()
                                }
                            }
                        }
//                        }

                    }
                }


            }

            fun bind(day: WeekDay) {
                this.day = day
                bind.exSevenDateText.text = dateFormatter.format(day.date)
                bind.exSevenDayText.text = day.date.dayOfWeek.displayText()

                if (YearMonth.now().month.toString().equals(selectedMonth)) {

                    if (day.date >= LocalDate.now() && day.date <= YearMonth.now().atEndOfMonth()) {

                        val colorRes = if (day.date == selectedDate) {
                            R.color.teal_700
                        } else {
                            R.color.sky
                        }
                        bind.llbg.setBackgroundColor(view.context.getColorCompat(colorRes))

                        if (day.date == selectedDate) {

                            bind.exSevenDayText.setTextColor(view.context.getColorCompat(R.color.white))
                            bind.exSevenDateText.setTextColor(view.context.getColorCompat(R.color.white))
                        } else {

                            bind.exSevenDayText.setTextColor(view.context.getColorCompat(R.color.black))
                            bind.exSevenDateText.setTextColor(view.context.getColorCompat(R.color.black))
                        }
                    } else {
                        bind.llbg.setBackgroundColor(view.context.getColorCompat(R.color.white_light))
                        bind.exSevenDayText.setTextColor(view.context.getColorCompat(R.color.grey_text2))
                        bind.exSevenDateText.setTextColor(view.context.getColorCompat(R.color.grey_text2))

                    }
                } else {

//                    if (day.date>=YearMonth.now().atStartOfMonth() && day.date<=YearMonth.now().atEndOfMonth()){

                    if (day.date.month.toString().equals(selectedMonth)) {
                        val colorRes = if (day.date == selectedDate) {
                            R.color.teal_700
                        } else {
                            R.color.sky
                        }
                        bind.llbg.setBackgroundColor(view.context.getColorCompat(colorRes))

                        if (day.date == selectedDate) {

                            bind.exSevenDayText.setTextColor(view.context.getColorCompat(R.color.white))
                            bind.exSevenDateText.setTextColor(view.context.getColorCompat(R.color.white))
                        } else {

                            bind.exSevenDayText.setTextColor(view.context.getColorCompat(R.color.black))
                            bind.exSevenDateText.setTextColor(view.context.getColorCompat(R.color.black))
                        }
                    } else {
                        bind.llbg.setBackgroundColor(view.context.getColorCompat(R.color.white_light))
                        bind.exSevenDayText.setTextColor(view.context.getColorCompat(R.color.grey_text2))
                        bind.exSevenDateText.setTextColor(view.context.getColorCompat(R.color.grey_text2))

                    }

//                    }


                }

            }
        }




        fragmentBookSlotBinding.exSevenCalendar.dayBinder =
            object : WeekDayBinder<DayViewContainer> {
                @RequiresApi(Build.VERSION_CODES.O)
                override fun create(view: View) = DayViewContainer(view)

                @RequiresApi(Build.VERSION_CODES.O)
                override fun bind(container: DayViewContainer, data: WeekDay) = container.bind(data)
            }


        fragmentBookSlotBinding.exSevenCalendar.weekScrollListener = { weekDays ->
            fragmentBookSlotBinding.tvMonth.text = getWeekPageTitle(weekDays)
        }


        fragmentBookSlotBinding.tvMonth.setOnClickListener {

            popupMonths()
        }



        fragmentBookSlotBinding.btnMorning.setOnClickListener {

            fragmentBookSlotBinding.btnMorning.setBackgroundResource(R.drawable.grey_blue_rounded)
            fragmentBookSlotBinding.btnAfternoon.setBackgroundResource(R.drawable.grey_sky_rounded)
            fragmentBookSlotBinding.btnEvening.setBackgroundResource(R.drawable.grey_sky_rounded)
            fragmentBookSlotBinding.btnMorning.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.white
                )
            )
            fragmentBookSlotBinding.btnAfternoon.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.black
                )
            )
            fragmentBookSlotBinding.btnEvening.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.black
                )
            )

            timeSlotAdapter?.updateData(morningtimeslotModelArrayList)


        }


        fragmentBookSlotBinding.btnAfternoon.setOnClickListener {

            fragmentBookSlotBinding.btnMorning.setBackgroundResource(R.drawable.grey_sky_rounded)
            fragmentBookSlotBinding.btnAfternoon.setBackgroundResource(R.drawable.grey_blue_rounded)
            fragmentBookSlotBinding.btnEvening.setBackgroundResource(R.drawable.grey_sky_rounded)

            fragmentBookSlotBinding.btnMorning.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.black
                )
            )
            fragmentBookSlotBinding.btnAfternoon.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.white
                )
            )
            fragmentBookSlotBinding.btnEvening.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.black
                )
            )

            timeSlotAdapter?.updateData(afternoontimeslotModelArrayList)

        }



        fragmentBookSlotBinding.btnEvening.setOnClickListener {

            fragmentBookSlotBinding.btnMorning.setBackgroundResource(R.drawable.grey_sky_rounded)
            fragmentBookSlotBinding.btnAfternoon.setBackgroundResource(R.drawable.grey_sky_rounded)
            fragmentBookSlotBinding.btnEvening.setBackgroundResource(R.drawable.grey_blue_rounded)

            fragmentBookSlotBinding.btnMorning.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.black
                )
            )
            fragmentBookSlotBinding.btnAfternoon.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.black
                )
            )
            fragmentBookSlotBinding.btnEvening.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.white
                )
            )

            timeSlotAdapter?.updateData(eveningtimeslotModelArrayList)

        }




        fragmentBookSlotBinding.btnDaily.setOnClickListener {

            fragmentBookSlotBinding.btnDaily.setBackgroundResource(R.drawable.rounded_border_blue_btn)
            fragmentBookSlotBinding.btnOnceaweek.setBackgroundResource(R.drawable.rounded_border_grey_btn)
            fragmentBookSlotBinding.btnTwiceaweek.setBackgroundResource(R.drawable.rounded_border_grey_btn)
            fragmentBookSlotBinding.btnDaily.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.white
                )
            )
            fragmentBookSlotBinding.btnOnceaweek.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.grey_text
                )
            )
            fragmentBookSlotBinding.btnTwiceaweek.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.grey_text
                )
            )
            bookingOption = "DAILY"

        }



        fragmentBookSlotBinding.btnOnceaweek.setOnClickListener {

            fragmentBookSlotBinding.btnDaily.setBackgroundResource(R.drawable.rounded_border_grey_btn)
            fragmentBookSlotBinding.btnOnceaweek.setBackgroundResource(R.drawable.rounded_border_blue_btn)
            fragmentBookSlotBinding.btnTwiceaweek.setBackgroundResource(R.drawable.rounded_border_grey_btn)
            fragmentBookSlotBinding.btnDaily.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.grey_text
                )
            )
            fragmentBookSlotBinding.btnOnceaweek.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.white
                )
            )
            fragmentBookSlotBinding.btnTwiceaweek.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.grey_text
                )
            )


            val pickupinFormat = SimpleDateFormat("dd-MM-yyyy")
            val date = pickupinFormat.parse(Shared_Preferences.getSelectedPickupdate().toString())
            val pickupoutFormat = SimpleDateFormat("EEEE")


            val deliveryinFormat = SimpleDateFormat("dd-MM-yyyy")
            val deliverydate =
                deliveryinFormat.parse(Shared_Preferences.getSelectedDeliverydate().toString())
            val deliveryoutFormat = SimpleDateFormat("EEEE")


            pickUpDayName = pickupoutFormat.format(date).toString()
            deliveryDayName = deliveryoutFormat.format(deliverydate).toString()
            bookingOption = "ONCE_IN_A_WEEK"

            Log.d(TAG, "pickupday and deliveryday -->"+pickUpDayName + " , "+deliveryDayName)
        }



        fragmentBookSlotBinding.btnTwiceaweek.setOnClickListener {

            fragmentBookSlotBinding.btnDaily.setBackgroundResource(R.drawable.rounded_border_grey_btn)
            fragmentBookSlotBinding.btnOnceaweek.setBackgroundResource(R.drawable.rounded_border_grey_btn)
            fragmentBookSlotBinding.btnTwiceaweek.setBackgroundResource(R.drawable.rounded_border_blue_btn)
            fragmentBookSlotBinding.btnDaily.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.grey_text
                )
            )
            fragmentBookSlotBinding.btnOnceaweek.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.grey_text
                )
            )
            fragmentBookSlotBinding.btnTwiceaweek.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.white
                )
            )

            val pickupinFormat = SimpleDateFormat("dd-MM-yyyy")
            val date = pickupinFormat.parse(Shared_Preferences.getSelectedPickupdate().toString())
            val pickupoutFormat = SimpleDateFormat("EEEE")


            val deliveryinFormat = SimpleDateFormat("dd-MM-yyyy")
            val deliverydate =
                deliveryinFormat.parse(Shared_Preferences.getSelectedDeliverydate().toString())
            val deliveryoutFormat = SimpleDateFormat("EEEE")


            pickUpDayName = pickupoutFormat.format(date).toString()
            deliveryDayName = deliveryoutFormat.format(deliverydate).toString()
            bookingOption = "TWICE_IN_A_WEEK"

            Log.d(TAG, "pickupday and deliveryday -->"+pickUpDayName + " , "+deliveryDayName)


        }






        fragmentBookSlotBinding.btnPickupslot.setOnClickListener {

            isDelivery = false
            fragmentBookSlotBinding.btnPickupslot.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.teal_700
                )
            )
            fragmentBookSlotBinding.btnDeliveryslot.setTextColor(
                ContextCompat.getColor(
                    mainActivity,
                    R.color.grey_text
                )
            )
            timeSlotAdapter?.updateData(morningtimeslotModelArrayList)
            fragmentBookSlotBinding.exSevenCalendar.setup(
                currentMonth.atStartOfMonth(),
                currentMonth.atEndOfMonth(),
                firstDayOfWeekFromLocale().minus(1),
            )

        }


        fragmentBookSlotBinding.btnDeliveryslot.setOnClickListener {

            if (Shared_Preferences.getSelectedPickupdate().toString().isEmpty()) {

                Toast.makeText(mainActivity, "Select Pickup Date!", Toast.LENGTH_SHORT).show()

            } else if (Shared_Preferences.getSelectedPickuptime().toString().isEmpty()) {

                Toast.makeText(mainActivity, "Select Pickup Time!", Toast.LENGTH_SHORT).show()

            } else {
                isDelivery = true
                fragmentBookSlotBinding.btnPickupslot.setTextColor(
                    ContextCompat.getColor(
                        mainActivity,
                        R.color.grey_text
                    )
                )
                fragmentBookSlotBinding.btnDeliveryslot.setTextColor(
                    ContextCompat.getColor(
                        mainActivity,
                        R.color.teal_700
                    )
                )
                timeSlotAdapter?.updateData(morningtimeslotModelArrayList)
                fragmentBookSlotBinding.exSevenCalendar.setup(
                    currentMonth.atStartOfMonth(),
                    currentMonth.atEndOfMonth(),
                    firstDayOfWeekFromLocale().minus(1),
                )

            }


        }


        fragmentBookSlotBinding.btnNext.setOnClickListener {


            if (Shared_Preferences.getSelectedDeliverydate().toString().isEmpty()) {

                Toast.makeText(mainActivity, "Select Delivery Date!", Toast.LENGTH_SHORT).show()
            } else if (Shared_Preferences.getSelectedDeliverytime().toString().isEmpty()) {

                Toast.makeText(mainActivity, "Select Delivery Time!", Toast.LENGTH_SHORT).show()

            } else {

                if (bookingtype.equals("repeatslot")) {

                    if (bookingOption.isNotEmpty()) {
                        showBottomDialog(
                            Shared_Preferences.getSelectedPickupdate().toString(),
                            Shared_Preferences.getSelectedPickuptime().toString(),
                            Shared_Preferences.getSelectedDeliverydate().toString(),
                            Shared_Preferences.getSelectedDeliverytime().toString(), it
                        )
                    } else {
                        Toast.makeText(mainActivity, "Select Delivery Option!", Toast.LENGTH_SHORT)
                            .show()

                    }


                } else {

                    showBottomDialog(
                        Shared_Preferences.getSelectedPickupdate().toString(),
                        Shared_Preferences.getSelectedPickuptime().toString(),
                        Shared_Preferences.getSelectedDeliverydate().toString(),
                        Shared_Preferences.getSelectedDeliverytime().toString(), it
                    )
                }


            }

        }


//        fragmentBookSlotBinding.exSevenCalendar.setup(
//            currentMonth.minusMonths(5).atStartOfMonth(),
//            currentMonth.plusMonths(5).atEndOfMonth(),
//            firstDayOfWeekFromLocale(),
//        )

        fragmentBookSlotBinding.exSevenCalendar.setup(
            currentMonth.atStartOfMonth(),
            currentMonth.atEndOfMonth(),
            firstDayOfWeekFromLocale().minus(1),
        )

        Log.d(TAG, "Startday-->" + currentMonth.atStartOfMonth())
        Log.d(TAG, "Endday-->" + currentMonth.atEndOfMonth().minusDays(5))

        fragmentBookSlotBinding.exSevenCalendar.scrollToDate(LocalDate.now())


    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun popupMonths() {

        var radioGroup: RadioGroup? = null
        var radioButton: RadioButton? = null
        var currentmonth: RadioButton? = null
        var currentnextmonth: RadioButton? = null
        var currentnextaftermonth: RadioButton? = null
        dialog = Dialog(mainActivity)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)
        val params = WindowManager.LayoutParams()
        dialog?.setContentView(R.layout.alert_months)
        dialog?.setCancelable(true)
        params.copyFrom(dialog?.getWindow()?.getAttributes())
        params.height = WindowManager.LayoutParams.MATCH_PARENT
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.gravity = Gravity.CENTER
        dialog?.getWindow()?.setAttributes(params)
        dialog?.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.show()
        radioGroup = dialog!!.findViewById(R.id.radiomoths)
        currentmonth = dialog!!.findViewById(R.id.currentmonth)
        currentnextmonth = dialog!!.findViewById(R.id.currentnextmonth)
        currentnextaftermonth = dialog!!.findViewById(R.id.currentnextaftermonth)

        currentmonth.text = LocalDate.now().month.toString() + " " + LocalDate.now().year
        currentnextmonth.text =
            LocalDate.now().month.plus(1).toString() + " " + LocalDate.now().year
        currentnextaftermonth.text =
            LocalDate.now().month.plus(2).toString() + " " + LocalDate.now().year

        currentmonth?.setOnClickListener {

            radioButton = dialog!!.findViewById(radioGroup?.checkedRadioButtonId!!)
            fragmentBookSlotBinding.tvMonth.text = radioButton?.text


            fragmentBookSlotBinding.exSevenCalendar.setup(
                YearMonth.now().atStartOfMonth(),
                YearMonth.now().atEndOfMonth(),
                firstDayOfWeekFromLocale().minus(1),
            )

            selectedMonth = LocalDate.now().month.toString()

            dialog?.dismiss()

        }


        currentnextmonth?.setOnClickListener {

            radioButton = dialog!!.findViewById(radioGroup?.checkedRadioButtonId!!)
            fragmentBookSlotBinding.tvMonth.text = radioButton?.text
            fragmentBookSlotBinding.exSevenCalendar.setup(
                YearMonth.now().plusMonths(1).atStartOfMonth(),
                YearMonth.now().plusMonths(1).atEndOfMonth(),
                firstDayOfWeekFromLocale().minus(1),
            )
            selectedMonth = LocalDate.now().month.plus(1).toString()

            dialog?.dismiss()

        }


        currentnextaftermonth?.setOnClickListener {

            radioButton = dialog!!.findViewById(radioGroup?.checkedRadioButtonId!!)
            fragmentBookSlotBinding.tvMonth.text = radioButton?.text
            fragmentBookSlotBinding.exSevenCalendar.setup(
                YearMonth.now().plusMonths(2).atStartOfMonth(),
                YearMonth.now().plusMonths(2).atEndOfMonth(),
                firstDayOfWeekFromLocale().minus(1),
            )

            selectedMonth = LocalDate.now().month.plus(2).toString()

            dialog?.dismiss()

        }

    }


    private fun showBottomDialog(
        pickupDate: String,
        pickupTime: String,
        deliveryDate: String,
        deliveryTime: String, itview: View
    ) {
        var tvPickupslot: TextView? = null
        var tvDeliveryslot: TextView? = null
        var btnOrder: AppCompatButton? = null
        val bottomdialog = BottomSheetDialog(mainActivity, R.style.CustomBottomSheetDialog)
        val view = layoutInflater.inflate(R.layout.bottom_dialog_layout, null)
        tvPickupslot = view.findViewById(R.id.tvPickupslot)
        tvDeliveryslot = view.findViewById(R.id.tvDeliveryslot)
        btnOrder = view.findViewById(R.id.btnOrder)
        bottomdialog.setCancelable(true)
        btnOrder?.setOnClickListener {

            if (Shared_Preferences.getselectedAddress()!!.isNotEmpty()) {

                bookingslot(pickupDate, pickupTime, deliveryDate, deliveryTime, itview, booking)
//                val navController = Navigation.findNavController(itview)
//                navController.navigate(R.id.nav_home)
                bottomdialog.dismiss()

            } else {
                val builder = android.app.AlertDialog.Builder(mainActivity)
                builder.setMessage("Primary Address not set!! Add Primary Address?")
                builder.setPositiveButton(
                    "Ok"
                ) { dialog, which ->
                    val navController = Navigation.findNavController(itview)
                    navController.navigate(R.id.nav_add_address)
                    dialog.dismiss()
                    bottomdialog.dismiss()
                }

                builder.setNegativeButton(
                    "No"
                ) { dialog, which ->
                    dialog.dismiss()
                    bottomdialog.dismiss()

                }

                val alert = builder.create()
                alert.setOnShowListener { arg0 ->
                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                        .setTextColor(resources.getColor(R.color.blue))
                    alert.getButton(android.app.AlertDialog.BUTTON_NEGATIVE)
                        .setTextColor(resources.getColor(R.color.blue))
                }
                alert.show()

            }


        }
        tvPickupslot?.text = pickupDate + " | " + pickupTime
        tvDeliveryslot?.text = deliveryDate + " | " + deliveryTime
        bottomdialog.setContentView(view)
        bottomdialog.show()


    }


    private fun bookingslot(
        pickupDate: String,
        pickupTime: String,
        deliveryDate: String,
        deliveryTime: String,
        view: View,
        booking: String
    ) {

        if (bookingtype.equals("repeatslot")) {
            if (bookingOption.isNotEmpty()) {
                if (Utilities.isNetworkAvailable(mainActivity)) {
                    viewModel.slotbooking(
                        devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                        source = "mob", SlotBookingRequestModel(
                            fullAddress = Shared_Preferences.getselectedAddress().toString(),
                            pickUpDate = pickupDate,
                            pickUpTime = pickupTime,
                            deliveryDate = deliveryDate,
                            deliveryTime = deliveryTime,
                            latitude = Shared_Preferences.getselectedLat().toString(),
                            longitude = Shared_Preferences.getselectedLon().toString(),
                            bookingType = booking,
                            bookingOption = bookingOption,
                            deliveryDayName = deliveryDayName,
                            pickUpDayName = pickUpDayName
                        )
                    )
                        .observe(mainActivity) {
                            it?.let { resource ->
                                when (resource.status) {
                                    Status.SUCCESS -> {
                                        mainActivity.hideProgressDialog()
                                        resource.data?.let { itResponse ->

                                            if (itResponse.status) {
                                                val builder =
                                                    android.app.AlertDialog.Builder(mainActivity)
                                                builder.setMessage(itResponse.message)
                                                builder.setPositiveButton(
                                                    "Ok"
                                                ) { dialog, which ->

                                                    val navController =
                                                        Navigation.findNavController(view)
                                                    navController.navigate(R.id.nav_home)
                                                    dialog.dismiss()
                                                }
                                                val alert = builder.create()
                                                alert.setOnShowListener { arg0 ->
                                                    alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                                        .setTextColor(resources.getColor(R.color.blue))
                                                }
                                                alert.show()

                                            } else {

                                                Toast.makeText(
                                                    mainActivity,
                                                    resource.data.message,
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            }
                                        }


                                    }
                                    Status.ERROR -> {
                                        mainActivity.hideProgressDialog()
                                        val builder = AlertDialog.Builder(mainActivity)
                                        builder.setMessage(it.message)
                                        builder.setPositiveButton(
                                            "Ok"
                                        ) { dialog, which ->

                                            dialog.cancel()

                                        }
                                        val alert = builder.create()
                                        alert.show()
                                    }

                                    Status.LOADING -> {
                                        mainActivity.showProgressDialog()
                                    }

                                }

                            }
                        }

                } else {
                    Toast.makeText(
                        mainActivity,
                        "Ooops! Internet Connection Error",
                        Toast.LENGTH_SHORT
                    )
                        .show()
                }

            } else {
                Toast.makeText(mainActivity, "Select Booking Options", Toast.LENGTH_SHORT).show()
            }

        } else {

            if (Utilities.isNetworkAvailable(mainActivity)) {
                viewModel.slotbooking(
                    devicetype = "android", key = "d77d7bd089b6ea50c35aff32c2ff4608",
                    source = "mob", SlotBookingRequestModel(
                        fullAddress = Shared_Preferences.getselectedAddress().toString(),
                        pickUpDate = pickupDate,
                        pickUpTime = pickupTime,
                        deliveryDate = deliveryDate,
                        deliveryTime = deliveryTime,
                        latitude = Shared_Preferences.getselectedLat().toString(),
                        longitude = Shared_Preferences.getselectedLon().toString(),
                        bookingType = booking,
                        bookingOption = bookingOption,
                        deliveryDayName = "",
                        pickUpDayName = ""
                    )
                )
                    .observe(mainActivity) {
                        it?.let { resource ->
                            when (resource.status) {
                                Status.SUCCESS -> {
                                    mainActivity.hideProgressDialog()
                                    resource.data?.let { itResponse ->

                                        if (itResponse.status) {
                                            val builder =
                                                android.app.AlertDialog.Builder(mainActivity)
                                            builder.setMessage(itResponse.message)
                                            builder.setPositiveButton(
                                                "Ok"
                                            ) { dialog, which ->

                                                val navController =
                                                    Navigation.findNavController(view)
                                                navController.navigate(R.id.nav_home)
                                                dialog.dismiss()
                                            }
                                            val alert = builder.create()
                                            alert.setOnShowListener { arg0 ->
                                                alert.getButton(android.app.AlertDialog.BUTTON_POSITIVE)
                                                    .setTextColor(resources.getColor(R.color.blue))
                                            }
                                            alert.show()

                                        } else {

                                            Toast.makeText(
                                                mainActivity,
                                                resource.data.message,
                                                Toast.LENGTH_SHORT
                                            ).show()

                                        }
                                    }


                                }
                                Status.ERROR -> {
                                    mainActivity.hideProgressDialog()
                                    val builder = AlertDialog.Builder(mainActivity)
                                    builder.setMessage(it.message)
                                    builder.setPositiveButton(
                                        "Ok"
                                    ) { dialog, which ->

                                        dialog.cancel()

                                    }
                                    val alert = builder.create()
                                    alert.show()
                                }

                                Status.LOADING -> {
                                    mainActivity.showProgressDialog()
                                }

                            }

                        }
                    }

            } else {
                Toast.makeText(mainActivity, "Ooops! Internet Connection Error", Toast.LENGTH_SHORT)
                    .show()
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()


        Shared_Preferences.setSelectedDeliverydate("")
        Shared_Preferences.setSelectedDeliverytime("")

        Shared_Preferences.setSelectedPickupdate("")
        Shared_Preferences.setSelectedPickuptime("")
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(
        position: Int,
        view: View,
        mTimeslotModelArrayList: ArrayList<TimeSlotModel>
    ) {


        selectedTime = mTimeslotModelArrayList[position].time
        val input = SimpleDateFormat("yyyy-MM-dd")
        val output = SimpleDateFormat("dd-MM-yyyy")
        var d: Date? = null
        try {
            d = input.parse(selectedDate.toString())
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        val selectedformatdate: String = output.format(d)

        if (isDelivery) {

            Shared_Preferences.setSelectedDeliverydate(selectedformatdate)
            Shared_Preferences.setSelectedDeliverytime(selectedTime)

        } else {

            Shared_Preferences.setSelectedPickupdate(selectedformatdate)
            Shared_Preferences.setSelectedPickuptime(selectedTime)

        }


    }

}