package com.cerverae18.superlistfinal


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import com.cerverae18.superlistfinal.databinding.ActivityNewListBinding
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.cerverae18.superlistfinal.fragments.NewListProductCellFragment
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class NewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewListBinding
    private  var listDate: Long? = null

    private lateinit var listNameEditText: EditText
    private var dateWasSelected = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        listNameEditText = binding.listNameEditText

        val products  = listOf<Product>(Product("Pizza", Category("FOOD")), Product("Eggs", Category("FOOD")), Product("Cooking Oil", Category("Cooking")))
        val productsAddedToList : HashMap<Product, Int> = hashMapOf()

        //ADD ALL PRODUCTS
        // TODO()  GET DATA FROM DATABASE FOR PRODUCTS
        if(savedInstanceState == null) {
            products.forEach { product ->
                val frag = NewListProductCellFragment.newInstance(product, productsAddedToList)
                supportFragmentManager.beginTransaction().add(R.id.newListProductsFrags, frag)
                    .commit()
            }
        }


        binding.btnSelectDate.setOnClickListener {
            removeFocusEditText(listNameEditText)
            listNameEditText.isEnabled = false
            listDate = Calendar.getInstance().timeInMillis
            showCalendarPopUpView(it)
        }

        binding.btnSaveList.setOnClickListener {
            var missingFields = 0
            val noName = listNameEditText.text.toString() == ""
            val noProducts = productsAddedToList.isEmpty()
            val noDate = !dateWasSelected

            Log.i("EACS", productsAddedToList.toString())
            if(noDate || noName || noProducts){
              createAlertDialog(R.string.missing_information, R.string.missing_information_message)
              return@setOnClickListener
            }


           //TODO() ADD LIST TO DATABASE AND SEND TO MAIN MENU
            finish()
        }

        listNameEditText.setOnKeyListener { view, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER){
                removeFocusEditText(view as EditText)
                 return@setOnKeyListener true
            }
             false
        }



    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun createAlertDialog(title: Int, message: Int){
        val dialog = AlertDialog.Builder(this)
            .setTitle(getString(title))
            .setMessage(getString(message))
            .setNegativeButton(R.string.cancel) { view, _ ->
                //Toast.makeText(this, "Cancel button pressed", Toast.LENGTH_SHORT).show()
                view.dismiss()
            }
            .setCancelable(false)
            .create()

        dialog.show()
    }


    private fun removeFocusEditText(editText : EditText){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
        editText.isFocusable = false
        editText.isFocusableInTouchMode = true
    }


    private fun showCalendarPopUpView(view: View){
        val inflater = layoutInflater
        val  popupView = inflater.inflate(R.layout.new_list_select_date_popup, null)
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
            dateWasSelected = true
             popupWindow.dismiss()
             listNameEditText.isEnabled = true
        }

        btnCancel.setOnClickListener {
            popupWindow.dismiss()
            listNameEditText.isEnabled = true
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)


    }

}