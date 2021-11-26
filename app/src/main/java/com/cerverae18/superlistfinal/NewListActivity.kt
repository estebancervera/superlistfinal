package com.cerverae18.superlistfinal


import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.text.InputFilter
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.cerverae18.superlistfinal.databinding.ActivityNewListBinding
import com.cerverae18.superlistfinal.fragments.NewListProductCellFragment
import com.cerverae18.superlistfinal.logic.*
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.ProductListCrossRef
import java.text.SimpleDateFormat
import java.util.*

/**
 * An Activity to add a new List
 *
 * This class creates an Activity for the User to create a new List
 *  by giving it a name, date and selecting products and quantities assign to that list.
 *
 *
 * @property binding the reference to the UI components in the layout associated to the Activity.
 * @property listDate the date timestamp selected by the User assigned to the to be created List
 * @property listNameEditText the EditText for the input of the to be created List
 * @property dateWasSelected flag that represents if a date has already been selected or not
 * @property productsAddedToList HashMap<Product, Int> to store the products and quantities selected by the user
 * @property productViewModel ViewModel that allows async operations to the database involving Products
 * @property listViewModel ViewModel that allows async operations to the database involving List
 * @property productListViewModel ViewModel that allows async operations to the database involving ProductListCrossRef

 */
class NewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewListBinding
    private  var listDate: Long? = null

    private lateinit var listNameEditText: EditText
    private var dateWasSelected = false

    private lateinit var productsAddedToList: HashMap<Product, Int>

    /* View Models */
    private val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as GeneralApplication).productRepository)
    }

    private val listViewModel: ListViewModel by viewModels {
        ListViewModelFactory((application as GeneralApplication).listRepository)
    }

    private val productListViewModel: ProductListViewModel by viewModels {
        ProductListViewModelFactory((application as GeneralApplication).productListRepository)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(EXTRA.getThemeColor(this))
        binding = ActivityNewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Sets up the back arrow for the user and runs the code on onSupportNavigateUp()
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
       // Changes the title of the ActionBar
        this.supportActionBar?.title = getString(R.string.new_list_title)

        listNameEditText = binding.listNameEditText
        // Adds a MAX number of characters to the listNameEditText editText
        listNameEditText.filters = arrayOf(InputFilter.LengthFilter(30))

        //initializes the hashmap
        productsAddedToList  = hashMapOf()


        //Gets the list of products asynchronously from the database and observes for changes
        productViewModel.allProducts.observe(this, { products ->
            // Renders the NewListProductCellFragments inside a scrollView,
            setupProductFrags(products, productsAddedToList)
        })


        // Creates a OnClickListener and assigns it to the selectDate button
        binding.btnSelectDate.setOnClickListener {
            //removes the Focus out of the list name EditText to the keyboard disappears
            removeFocusEditText(listNameEditText)
            listNameEditText.isEnabled = false
            // Default date is the current to setup calendar view
            listDate = Calendar.getInstance().timeInMillis
            //presents a popupView for the user to select a date
            showCalendarPopUpView(it)
        }
        // Creates a OnClickListener and assigns it to the saveList button
        binding.btnSaveList.setOnClickListener {

            val noName = listNameEditText.text.toString() == ""
            val noProducts =  productsAddedToList.isEmpty()
            val noDate = !dateWasSelected

            //checks that the users can't create a list with missing information
            if(noDate || noName || noProducts){
              createAlertDialog(R.string.missing_information, R.string.missing_information_message)
              return@setOnClickListener
            }
            val name = listNameEditText.text.toString()
            var timestamp = 0L
                listDate?.let {  timestamp = listDate as Long }
            //creates a random UUID for the list
            val uuid = UUID.randomUUID().toString()
            // Inserts a new list into the database with the user's input
            listViewModel.insert(com.cerverae18.superlistfinal.logic.entities.List( uuid, name, timestamp))
            //iterates through the hashmap
             for ((product, qty) in productsAddedToList) {
                //Inserts a productListCrossRed object to the database associated to the newly created list
                productListViewModel.insert(ProductListCrossRef(product.productId, qty, uuid))
             }

            //returns to previous activity by finishing the intent associated with this one
            finish()
        }

        //Creates and assigns a OnKeyListener to the listNameEditText
        listNameEditText.setOnKeyListener { view, keyCode, _ ->
            if (keyCode == KeyEvent.KEYCODE_ENTER){
                //if user preses enter remove all focus out of the editText
                removeFocusEditText(view as EditText)
                 return@setOnKeyListener true
            }
             false
        }



    }

    /**
     * This method is called whenever the user chooses to navigate Up within your application's activity hierarchy from the action bar.
     * @return True if the activity completed successfully and ended and false if not.
     */

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    /**
     *  This method creates an AlertDialog with a cancel button, title and message passed, and shows it
     *  @param title is the String ID assigned to a String Resources to be used as title of the dialog
     *  @param message is the String ID assigned to a String Resources to be used as body message of the dialog
     */

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

    /**
     *  This method removes the Focus out of an EditText instance
     *  @param editText represents the EditText that will loose focus
     *
     */
    private fun removeFocusEditText(editText : EditText){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
        editText.isFocusable = false
        editText.isFocusableInTouchMode = true
    }

    /**
     *  This method deletes all previous fragments and renders a list of NewListProductCellFragment in an scrollview
     *  @param products is a list of Products
     *  @param hashMap is a HashMap<Product, Int> that will be passed to the fragment to keep track of the quantity selected by the user
     *
     */
    private fun setupProductFrags(products : List<Product>, hashMap: HashMap<Product, Int>){
        //deletes all fragments
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        //renders all NewListProductCellFragments based on the product list passed
        products.forEach { product ->
            val frag = NewListProductCellFragment.newInstance(product, hashMap)
            supportFragmentManager.beginTransaction().add(R.id.newListProductsFrags,frag).commit()
        }
    }

    /**
     *  This method shows a PopupWindows with a calendar picker, and buttons for the user to select a date
     *  @param view is the view that will present the PopupWindow
     */
    @SuppressLint("SimpleDateFormat", "InflateParams")
    private fun showCalendarPopUpView(view: View){
        val inflater = layoutInflater
        //inflates the new_list_select_date_popup.xml layout
        val  popupView = inflater.inflate(R.layout.new_list_select_date_popup, null)

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = false // allows the window to have focus

        // create the popup window
        val popupWindow = PopupWindow(popupView, width, height, focusable)
        // creates a shadow to simulate the view is higher than the background
        popupWindow.elevation = 20f

        val btnSelect = popupView.findViewById<Button>(R.id.new_list_confirm_date_btn)
        val btnCancel = popupView.findViewById<Button>(R.id.new_list_cancel_date_btn)
        val calendar = popupView.findViewById<CalendarView>(R.id.new_list_date_select)
        // asigns the calendarPicker date to the listDate property
        calendar.date = listDate!!

        // Calendar instance that will keep track of the users selected date
        val calender: Calendar = Calendar.getInstance()
        //Sets the minimum date for the calendarPicker to the current day
        calendar.minDate = calender.timeInMillis
        // Format the date will be showed in text
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        var tempDate: Long = calendar.date
        //creates and assigns a SetOnDateChangeListener to the calendarPicker
        calendar.setOnDateChangeListener { calendarView, y, m, d ->
            // Set attributes in calender object as per selected date.
            calender.set(y, m, d)
            //animates and changes the date in the calendar view
            calendarView.setDate(calender.timeInMillis, true, true)
            tempDate = calendarView.date
        }
        //Creates a OnClickListener for the Select Button
        btnSelect.setOnClickListener {
            //displays the selected date in the date label
             binding.selectedDateText.text = sdf.format(tempDate)
            // assigns the calendar chosen date to the listDate property
             listDate = calendar.date
            // flags that a date was actually selected
            dateWasSelected = true
            //dismisses the popupWindows
             popupWindow.dismiss()
            //enables the name editText
             listNameEditText.isEnabled = true
        }

        btnCancel.setOnClickListener {
            //dismisses the popupWindows
            popupWindow.dismiss()
            //enables the name editText
            listNameEditText.isEnabled = true
        }
        //Renders the popupWindow centered at the view passed in the parameters
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)


    }

}