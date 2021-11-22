package com.cerverae18.superlistfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import com.cerverae18.superlistfinal.databinding.ActivityMasterListBinding
import android.content.DialogInterface
import android.view.Gravity
import android.view.View
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

        productViewModel.allSongs.observe(this, { products ->
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
        val focusable = false // lets taps outside the popup also dismiss it

        val popupWindow = PopupWindow(popupView, width, height, focusable)
        popupWindow.elevation = 20f

        val categoryPicker = popupView.findViewById<Spinner>(R.id.categoryPicker)
        val btnCancel = popupView.findViewById<Button>(R.id.master_list_popup_btn_cancel)
        val btnAdd = popupView.findViewById<Button>(R.id.master_list_popup_add_btn)
        val editTextName = popupView.findViewById<EditText>(R.id.new_product_edit_name)

        val spinnerArrayAdapter: ArrayAdapter<String> = ArrayAdapter<String>(
            this, android.R.layout.simple_spinner_item, categories.map { category -> category.name  }
        )
        categoryPicker.adapter = spinnerArrayAdapter

        btnAdd.setOnClickListener {
            //TODO() CHECK IF NULL ANY VALUE AND SHOW ALERT
            val name = editTextName.text.toString()
            val category =  categoryPicker.selectedItemPosition

            addProduct(name, category)

        }


        btnCancel.setOnClickListener {
            popupWindow.dismiss()
        }

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0)


    }

    private fun setupProductFrags(products : List<Product>){
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        products.forEach { product ->
            val frag = MasterListCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.master_list_products_frags,frag).commit()
        }
    }

    private fun addProduct(name: String, category: Int){
        productViewModel.insert(Product(name, category))
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}