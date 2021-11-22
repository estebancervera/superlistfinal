package com.cerverae18.superlistfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import com.cerverae18.superlistfinal.databinding.ActivityMasterListBinding
import android.content.DialogInterface

import android.widget.LinearLayout
import com.cerverae18.superlistfinal.fragments.MasterListCellFragment
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product

class MasterListActivity : AppCompatActivity() {

    //AlertDialog used to add a new product to the master list
    lateinit var dialog: AlertDialog
    lateinit var dialogBuilder: AlertDialog.Builder
    val dialogItems = arrayOf("Yes", "No")
    var dialogResult = ""
    //lateinit var nameEditText: EditText
    //lateinit var categoryEditText: EditText
    //lateinit var layout: LinearLayout

    //Binding to retrieve this activity's views
    private lateinit var binding: ActivityMasterListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMasterListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        dialogBuilder = AlertDialog.Builder(this)

        val btnAddProduct = binding.btnAddProduct

        btnAddProduct.setOnClickListener {

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
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}