package com.cerverae18.superlistfinal

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.widget.Switch
import android.widget.TextView
import androidx.activity.viewModels
import com.cerverae18.superlistfinal.databinding.ActivityListBinding
import com.cerverae18.superlistfinal.fragments.ListProductCellFragment
import com.cerverae18.superlistfinal.logic.*
import com.cerverae18.superlistfinal.logic.entities.relations.ListWithProducts
import java.text.SimpleDateFormat


class ListActivity : AppCompatActivity() {

    // Binding to retrieve this activity's views
    private lateinit var binding: ActivityListBinding
    private lateinit var products: List<ListWithProducts>
    private lateinit var sortingSwitch: Switch

    val productListViewModel: ProductListViewModel by viewModels {
        ProductListViewModelFactory((application as GeneralApplication).productListRepository)
    }

    val listViewModel: ListViewModel by viewModels {
        ListViewModelFactory((application as GeneralApplication).listRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(EXTRA.getThemeColor(this))
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //this.supportActionBar?.setDisplayShowTitleEnabled(false)



        val listId = intent.getStringExtra(EXTRA.EXTRA_LIST_ID)


        Log.i("EACS", "$listId")
        if (listId != null) {
            listViewModel.getListById(listId).observe(this, { list ->
                val sdf = SimpleDateFormat("dd/MM/yy")
                this.supportActionBar?.subtitle = sdf.format(list.date)
                this.supportActionBar?.title = list.name
            })

            productListViewModel.productsFromList(listId).observe(this, { products ->
                this.products = products
                setFragmentsByAlphabeticalOrder()

            })
        }


    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    private fun setFragmentsByAlphabeticalOrder() {
        for (product in products.sortedWith(compareBy({it.checked}, {it.name}))) {
            val frag = ListProductCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.list_activity_products_frags, frag).commit()
        }
    }

    private fun setFragmentsByCategory() {
        for (product in products.sortedWith(compareBy({it.checked},{it.category}, {it.name})) ){
            val frag = ListProductCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.list_activity_products_frags, frag).commit()
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
        if ( sortingSwitch.isChecked) {
            setFragmentsByCategory()
        } else {
            setFragmentsByAlphabeticalOrder()
        }
    }


}