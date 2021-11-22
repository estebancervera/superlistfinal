package com.cerverae18.superlistfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.cerverae18.superlistfinal.databinding.ActivityListBinding
import com.cerverae18.superlistfinal.fragments.ListProductCellFragment
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product

class ListActivity : AppCompatActivity() {

    // Binding to retrieve this activity's views
    private lateinit var binding: ActivityListBinding
    private lateinit var products: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        products = mutableListOf<Product>()
        val categories = mutableListOf<Category>(
            Category("Cereals"),
            Category("Proteins"),
//            Category("Drinks"),
//            Category("Dairy products"),
        )

        val c = listOf<String>("A","B", "C", "D", "E", "F", "G","H", "I", "J", "K", "L")

        for (i in 1..10) {
            products.add(Product(c.random(), categories.random()))
        }

        setFragmentsByAlphabeticalOrder(products)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setFragmentsByAlphabeticalOrder(products: MutableList<Product>) {
        for (product in products.sortedBy { it.name }) {
            val frag = ListProductCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.list_activity_products_frags, frag).commit()
        }
    }

    private fun setFragmentsByCategory(products: MutableList<Product>) {
        for (product in products.sortedWith(compareBy({it.category.name}, {it.name})) ){
            val frag = ListProductCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.list_activity_products_frags, frag).commit()
        }
    }

    private fun removeAllFragments() {
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.master_list -> {
                removeAllFragments()
                setFragmentsByCategory(products)
            }

        }
        return super.onOptionsItemSelected(item)
    }
}