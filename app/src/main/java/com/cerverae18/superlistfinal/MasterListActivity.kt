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
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory

<<<<<<< HEAD
=======
import android.widget.LinearLayout
import com.cerverae18.superlistfinal.fragments.MasterListCellFragment
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product
>>>>>>> master

class MasterListActivity : AppCompatActivity() {

    //AlertDialog used to add a new product to the master list
    lateinit var dialog: AlertDialog
    lateinit var dialogBuilder: AlertDialog.Builder

    private var categories = listOf<Category>()


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
            setupProductFrags(products)
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




<<<<<<< HEAD

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
            popupWindow.dismiss()

        }


        btnCancel.setOnClickListener {
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)


    }

    private fun setupProductFrags(products : List<ProductWithCategory>){
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        products.forEach { product ->
            val frag = MasterListCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.master_list_products_frags,frag).commit()
=======
            var layout:LinearLayout = LinearLayout(this)
            layout.orientation = LinearLayout.VERTICAL
            layout.layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.FILL_PARENT,
                LinearLayout.LayoutParams.FILL_PARENT
            )


            //layout.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.Fill_PARENT)
            var nameEditText: EditText = EditText(this)
            nameEditText.setPadding(5,10,5,20)
            nameEditText.hint = "Product's name"

            var categoryEditText: EditText = EditText(this)
            categoryEditText.setPadding(5,10,5,20)
            categoryEditText.hint = "Product's category"

            layout.addView(nameEditText)
            layout.addView(categoryEditText)

            //dialogBuilder.setView(nameEditText)
            //dialogBuilder.setView(categoryEditText)
            dialogBuilder.setView(layout)
            dialogBuilder.setTitle("Enter name of the product you wish to add")

            dialogBuilder.setPositiveButton("Create", DialogInterface.OnClickListener { dialog, whichButton ->
                //What ever you want to do with the value
                //val editTextVal: Editable = editText.text.toString()
                //OR
                val productName: String = nameEditText.text.toString()
                val productCategory: String = categoryEditText.text.toString()
                val newProduct: Product = Product( productName, Category(productCategory))

                var frag = MasterListCellFragment.newInstance(newProduct.name, newProduct.category.name)
                supportFragmentManager.beginTransaction()
                    .add(R.id.products, frag).commit()
                dialog.dismiss()
            })

            dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, whichButton ->
                // what ever you want to do with No option.
                dialog.dismiss()
            })

             dialogBuilder.show()
>>>>>>> master
        }
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
}