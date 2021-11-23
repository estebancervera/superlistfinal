package com.cerverae18.superlistfinal


import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import com.cerverae18.superlistfinal.databinding.ActivityNewListBinding
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
<<<<<<< HEAD
import com.cerverae18.superlistfinal.fragments.MasterListCellFragment
import com.cerverae18.superlistfinal.fragments.NewListProductCellFragment
import com.cerverae18.superlistfinal.logic.*
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory
=======
import com.cerverae18.superlistfinal.fragments.NewListProductCellFragment
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product
>>>>>>> master
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap


class NewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewListBinding
    private  var listDate: Long? = null

    private lateinit var listNameEditText: EditText
    private var dateWasSelected = false

    private lateinit var productsAddedToList: HashMap<Product, Int>

    private lateinit var lists : List<com.cerverae18.superlistfinal.logic.entities.List>


    val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as GeneralApplication).productRepository)
    }

    val listViewModel: ListViewModel by viewModels {
        ListViewModelFactory((application as GeneralApplication).listRepository)
    }

    val productListViewModel: ProductListViewModel by viewModels {
        ProductListViewModelFactory((application as GeneralApplication).productListRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        listNameEditText = binding.listNameEditText

        productsAddedToList  = hashMapOf()

        //ADD ALL PRODUCTS

        productViewModel.allProducts.observe(this, { products ->
            setupProductFrags(products, productsAddedToList)
        })

        listViewModel.allLists.observe(this, { lists ->
            this.lists = lists
        })


        binding.btnSelectDate.setOnClickListener {
            removeFocusEditText(listNameEditText)
            listNameEditText.isEnabled = false
            listDate = Calendar.getInstance().timeInMillis
            showCalendarPopUpView(it)
        }

        binding.btnSaveList.setOnClickListener {
            var missingFields = 0
            val noName = listNameEditText.text.toString() == ""
            val noProducts =  false//productsAddedToList.isEmpty()
            val noDate = !dateWasSelected


            if(noDate || noName || noProducts){
              createAlertDialog(R.string.missing_information, R.string.missing_information_message)
              return@setOnClickListener
            }
            val name = listNameEditText.text.toString()
            var timestamp: Long = 0L
                listDate?.let {  timestamp = listDate as Long }
            val uuid = UUID.randomUUID().toString()
            listViewModel.insert(com.cerverae18.superlistfinal.logic.entities.List( uuid, name, timestamp))

            Log.i("EACS", "ESTAMOS AFUERA")

             for ((product, qty) in productsAddedToList) {
                Log.i("EACS", "ESTAMOS ADENTRO")
                val productList = ProductListCrossRef(product.productId, qty, uuid)
                Log.i("EACS", "${productList.listId}")
                productListViewModel.insert(productList)
                }


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

    private fun setupProductFrags(products : List<Product>, hashMap: HashMap<Product, Int>){
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        products.forEach { product ->
            val frag = NewListProductCellFragment.newInstance(product, hashMap)
            supportFragmentManager.beginTransaction().add(R.id.newListProductsFrags,frag).commit()
        }
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