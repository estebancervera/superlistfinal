package com.cerverae18.superlistfinal


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.TimeUtils
import android.view.View
import android.view.ViewGroup
import com.cerverae18.superlistfinal.databinding.ActivityNewListBinding
import android.view.Gravity
import android.widget.*
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit


class NewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewListBinding
    private  var listDate: Long? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val products  = listOf<Product>(Product("Pizza", Category("FOOD")), Product("Eggs", Category("FOOD")), Product("Cooking Oil", Category("Cooking")))

        for (i in 1..20){
            val frag = NewListProductCellFragment.newInstance(products.first())

            supportFragmentManager.beginTransaction().add(R.id.newListProductsFrags,frag ).commit()

        }


        binding.btnSelectDate.setOnClickListener {
           listDate = Calendar.getInstance().timeInMillis
            showCalendarPopUpView(it)
        }


    }

    private fun showCalendarPopUpView(view: View){
        val inflater = layoutInflater
        val  popupView = inflater.inflate(R.layout.new_list_select_date_popup, null)
        // create the popup window
        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = false // lets taps outside the popup also dismiss it

        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.elevation = 20f

        val btnSelect = popupView.findViewById<Button>(R.id.new_list_confirm_date_btn)
        val btnCancel = popupView.findViewById<Button>(R.id.new_list_cancel_date_btn)
        val calendar = popupView.findViewById<CalendarView>(R.id.new_list_date_select)
        calendar.date = listDate!!
        val calender: Calendar = Calendar.getInstance()
        calendar.minDate = calender.timeInMillis
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var tempDate: Long = calendar.date
        calendar.setOnDateChangeListener { calendarView, y, m, d ->

            // Set attributes in calender object as per selected date.
            calender.set(y, m, d)

            calendarView.setDate(calender.timeInMillis, true, true)
            tempDate = calendarView.date


        }

        btnSelect.setOnClickListener {
             binding.selectedDateText.text = sdf.format(tempDate)
                listDate = calendar.date
            popupWindow.dismiss()
        }

        btnCancel.setOnClickListener {
            Log.i("TEST", "$listDate")
            popupWindow.dismiss() }

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)
//        popupView.setOnClickListener{
//           // popupWindow.dismiss()
//        }

    }

}