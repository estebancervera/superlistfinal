package com.cerverae18.superlistfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Switch
import com.cerverae18.superlistfinal.databinding.ActivityListBinding
import com.cerverae18.superlistfinal.fragments.ListProductCellFragment
import com.cerverae18.superlistfinal.logic.entities.Category
import com.cerverae18.superlistfinal.logic.entities.Product
import com.cerverae18.superlistfinal.logic.entities.relations.ProductWithCategory


class ListActivity : AppCompatActivity() {

    // Binding to retrieve this activity's views
    private lateinit var binding: ActivityListBinding
    private lateinit var products: MutableList<ProductWithCategory>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        products = mutableListOf<ProductWithCategory>()
        val categories = mutableListOf<Category>(
            Category("Cereals"),
            Category("Proteins"),
        )

        val c = listOf<String>("A","B", "C", "D", "E", "F", "G","H", "I", "J", "K", "L")

        for (i in 1..10) {
            products.add(ProductWithCategory(0, c.random(), categories.random().name))
        }

        setFragmentsByAlphabeticalOrder(products)

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setFragmentsByAlphabeticalOrder(products: MutableList<ProductWithCategory>) {
        for (product in products.sortedBy { it.name }) {
            val frag = ListProductCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.list_activity_products_frags, frag).commit()
        }
    }

    private fun setFragmentsByCategory(products: MutableList<ProductWithCategory>) {
        for (product in products.sortedWith(compareBy({it.category}, {it.name})) ){
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
        menuInflater.inflate(R.menu.menu_list, menu)
        val itemswitch = menu!!.findItem(R.id.sorting_switch)
        itemswitch.setActionView(R.layout.switch_item)

        val sw = menu.findItem(R.id.sorting_switch).actionView.findViewById(R.id.layout_switch) as Switch

        sw.setOnCheckedChangeListener { buttonView, isChecked ->
            removeAllFragments()
            if (isChecked) {
                setFragmentsByCategory(products)
            } else {
                setFragmentsByAlphabeticalOrder(products)
            }
        }
        return super.onCreateOptionsMenu(menu)
    }

}