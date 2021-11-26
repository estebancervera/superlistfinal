package com.cerverae18.superlistfinal

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.Switch
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import com.cerverae18.superlistfinal.databinding.ActivityListBinding
import com.cerverae18.superlistfinal.fragments.ListProductCellFragment
import com.cerverae18.superlistfinal.logic.*
import java.text.SimpleDateFormat
import com.cerverae18.superlistfinal.logic.entities.relations.ProductFromList

/**
 * An Activity to display the date list
 *
 * This class creates an Activity for the User to view a particular date list
 *  and selecting which products has been taken so far
 *
 * @property binding the reference to the UI components in the layout associated to the Activity.
 * @property products the list of the products from a list
 * @property sortingSwitch two-state toggle switch widget that can select between two options.
 * @property listViewModel ViewModel that allows async operations to the database involving List
 * @property productListViewModel ViewModel that allows async operations to the database involving ProductListCrossRef

 */
@SuppressLint("UseSwitchCompatOrMaterialCode")
class ListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityListBinding
    private lateinit var products: List<ProductFromList>
    private lateinit var sortingSwitch: Switch

    /* View Models */
    internal val productListViewModel: ProductListViewModel by viewModels {
        ProductListViewModelFactory((application as GeneralApplication).productListRepository)
    }

    private val listViewModel: ListViewModel by viewModels {
        ListViewModelFactory((application as GeneralApplication).listRepository)
    }

    @SuppressLint("SimpleDateFormat")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(EXTRA.getThemeColor(this))
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sets up the back arrow for the user and runs the code on onSupportNavigateUp()
        this.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // Get the selected list id from the extras passed in the intent
        val listId = intent.getStringExtra(EXTRA.EXTRA_LIST_ID)

        if (listId != null) {
            // If the id is not null get the selected list asynchronously from the database and observes for changes
            listViewModel.getListById(listId).observe(this, { list ->
              if ( list != null)  {
                  val sdf = SimpleDateFormat("dd/MM/yy")
                  // Set the list name and date as the title and subtitle of the Activity
                  this.supportActionBar?.subtitle = sdf.format(list.date)
                  this.supportActionBar?.title = list.name
              }
            })

            // Populate the products list with the database data
            productListViewModel.productsFromList(listId).observe(this, { products ->
                this.products = products
                // Set the activity's fragments by alphabetical order
                setFragmentsByAlphabeticalOrder()
            })
        }

        // Click listener for the delete list button
        binding.listActivityBtnDeleteList.setOnClickListener {
            if (listId != null) {
                createDeleteDialog(listId)
            }
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
     * This method add a number of [ListProductCellFragment] to the activity sorted by checked status first
     * and then by alphabetical ascending order (A to Z).
     */
    private fun setFragmentsByAlphabeticalOrder() {
        for (product in products.sortedWith(compareBy({it.checked}, {it.name}))) {
            val frag = ListProductCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.list_activity_products_frags, frag).commit()
        }
    }

    /**
     * This method add a number of [ListProductCellFragment] to the activity sorted by checked status first
     * then by category name in ascending order (A to Z) and finally by product name in each category
     * in alphabetical ascending order to (A to Z).
     */
    private fun setFragmentsByCategory() {
        for (product in products.sortedWith(compareBy({it.checked},{it.category}, {it.name})) ){
            val frag = ListProductCellFragment.newInstance(product)
            supportFragmentManager.beginTransaction().add(R.id.list_activity_products_frags, frag).commit()
        }
    }

    /**
     * This method remove all the fragments in the activity.
     */
    fun removeAllFragments() {
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
    }



    /**
     *  This method creates an [AlertDialog] to confirm the deletion of the list.
     *  @param listId the id of the list to be deleted.
     */
    private fun createDeleteDialog(listId: String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder
            .setTitle(getString(R.string.delete_list))
            .setMessage(getString(R.string.delete_list_message))
            .setPositiveButton(getString(R.string.yes), DialogInterface.OnClickListener { dialog, _ ->
                finish()
                listViewModel.delete(listId)
                dialog.dismiss()
            })
            .setNegativeButton(getString(R.string.cancel), DialogInterface.OnClickListener { dialog, _ ->
                dialog.dismiss()
            })

        dialogBuilder.show()
    }



    /**
     * Initialize the contents of the Activity's standard options menu.
     * @param menu The options menu in which you place your items.
     * @return You must return true for the menu to be displayed; if you return false it will not be shown.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_list, menu)

        sortingSwitch = menu?.findItem(R.id.sorting_switch)?.actionView?.findViewById(R.id.layout_switch) as Switch

        sortingSwitch.setOnCheckedChangeListener { _, _ ->
          updateOnChecked()
        }
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * This method remove and then add a number of [ListProductCellFragment] sorted depending
     * on the [sortingSwitch] state.
     */
    private fun updateOnChecked(){
        removeAllFragments()
        if (sortingSwitch.isChecked) {
            setFragmentsByCategory()
        } else {
            setFragmentsByAlphabeticalOrder()
        }
    }

}