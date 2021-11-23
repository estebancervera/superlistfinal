package com.cerverae18.superlistfinal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.cerverae18.superlistfinal.databinding.ActivityMasterListBinding
import android.content.DialogInterface
import android.util.Log
import android.view.Gravity
import android.view.KeyEvent
import android.view.Menu
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.activity.viewModels
import com.cerverae18.superlistfinal.fragments.MasterListCellFragment
import com.cerverae18.superlistfinal.logic.CategoryViewModel
import com.cerverae18.superlistfinal.logic.CategoryViewModelFactory
import com.cerverae18.superlistfinal.logic.ProductViewModel
import com.cerverae18.superlistfinal.logic.ProductViewModelFactory
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product
import java.text.SimpleDateFormat
import java.util.*
import android.widget.ArrayAdapter
import com.cerverae18.superlistfinal.fragments.ListProductCellFragment
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory


class MasterListActivity : AppCompatActivity() {

    //AlertDialog used to add a new product to the master list
    lateinit var dialog: AlertDialog
    lateinit var dialogBuilder: AlertDialog.Builder
    private lateinit var sortingSwitch: Switch

    private var categories = listOf<Category>()
    private lateinit var productsWithCategory: List<ProductWithCategory>


    //Binding to retrieve this activity's views
    private lateinit var binding: ActivityMasterListBinding

     val productViewModel: ProductViewModel by viewModels {
        ProductViewModelFactory((application as GeneralApplication).productRepository)
    }

    val categoryViewModel: CategoryViewModel by viewModels {
        CategoryViewModelFactory((application as GeneralApplication).categoryRepository)
    }




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        dialogBuilder = AlertDialog.Builder(this)

        productViewModel.allProductsWithCategories.observe(this, { products ->
            this.productsWithCategory = products
            updateOnChecked()
        })

        categoryViewModel.allCategories.observe(this, { categories ->
              this.categories = categories
        })

        val btnAddProduct = binding.btnAddProduct

        btnAddProduct.setOnClickListener {
            showNewProductPopUpView(it)
        }

    }



    private fun showNewProductPopUpView(view: View){
        val inflater = layoutInflater
        val  popupView = inflater.inflate(R.layout.master_list_new_product_popup, null)
        // create the popup window
        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true

        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.elevation = 20f





        val categoryPicker = popupView.findViewById<Spinner>(R.id.categoryPicker)
        val btnCancel = popupView.findViewById<Button>(R.id.master_list_popup_btn_cancel)
        val btnAdd = popupView.findViewById<Button>(R.id.master_list_popup_add_btn)
        val editTextName = popupView.findViewById<EditText>(R.id.new_product_edit_name)


        editTextName.setOnKeyListener { view, keyCode, keyEvent ->
            if (keyCode == KeyEvent.KEYCODE_ENTER){

                removeFocusEditText(view as EditText)
                return@setOnKeyListener true
            }
            false

        }

        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, categories.map { category -> category.name  }
        )
        categoryPicker.adapter = spinnerArrayAdapter


        btnAdd.setOnClickListener {
            //TODO() CHECK IF NULL ANY VALUE AND SHOW ALERT
            val name = editTextName.text.toString()
            val category =  categoryPicker.selectedItemPosition + 1

            addProduct(name, category)
            updateOnChecked()
            popupWindow.dismiss()

        }


        btnCancel.setOnClickListener {
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)


    }

    private fun addProduct(name: String, category: Int){
        productViewModel.insert(Product(name, category))
    }

    private fun removeFocusEditText(editText : EditText){
        val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(editText.windowToken, 0)
        editText.isFocusable = false
        editText.isFocusableInTouchMode = true
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setFragmentsByAlphabeticalOrder() {
        for (product in productsWithCategory.sortedWith(compareBy({it.name}))) {
            val frag = MasterListCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.master_list_products_frags, frag).commit()
        }
    }

    private fun setFragmentsByCategory() {
        for (product in productsWithCategory.sortedWith(compareBy({it.category}, {it.name})) ){
            val frag = MasterListCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.master_list_products_frags, frag).commit()
        }
    }

    fun removeAllFragments() {
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)
        val itemswitch = menu!!.findItem(R.id.sorting_switch)
        itemswitch.setActionView(R.layout.switch_item)

        sortingSwitch = menu.findItem(R.id.sorting_switch).actionView.findViewById(R.id.layout_switch) as Switch

        sortingSwitch.setOnCheckedChangeListener { _, state ->
            updateOnChecked()
        }
        return super.onCreateOptionsMenu(menu)
    }

    private fun updateOnChecked(){
        removeAllFragments()
        if (sortingSwitch.isChecked) {
            setFragmentsByCategory()
        } else {
            setFragmentsByAlphabeticalOrder()
        }
    }

}