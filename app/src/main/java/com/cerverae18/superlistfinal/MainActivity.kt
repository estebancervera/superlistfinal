package com.cerverae18.superlistfinal

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.activity.viewModels
import com.cerverae18.superlistfinal.databinding.ActivityMainBinding
import com.cerverae18.superlistfinal.logic.entities.List
import com.cerverae18.superlistfinal.fragments.MainActivityListCellFragment
import com.cerverae18.superlistfinal.logic.EXTRA

import com.cerverae18.superlistfinal.logic.ListViewModel
import com.cerverae18.superlistfinal.logic.ListViewModelFactory
import java.text.SimpleDateFormat
import java.util.*


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val listViewModel: ListViewModel by viewModels {
        ListViewModelFactory((application as GeneralApplication).listRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(EXTRA.getThemeColor(this))

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // this.supportActionBar?.setDisplayShowTitleEnabled(false)

        this.supportActionBar?.title = getString(R.string.lists_title)

        listViewModel.allLists.observe(this, { lists ->
            setupListFrags(lists)

        })





        Log.i("EACS", "${supportFragmentManager.fragments.size}")

//        lists.forEach { list ->
//            val frag = MainActivityListCellFragment.newInstance(list)
//            supportFragmentManager.beginTransaction().add(R.id.main_activity_lists_frags, frag).commit()
//
//        }
        /*
            Intent to New List Activity
         */
        binding.mainActivityBtnAddNewList.setOnClickListener {
            Intent(this, NewListActivity::class.java).also{
                startActivity(it)
            }
        }

    }

    private fun setupListFrags(lists : kotlin.collections.List<List>){
        for (fragment in supportFragmentManager.fragments) {
            supportFragmentManager.beginTransaction().remove(fragment).commit()
        }
        lists.forEach { list ->
            val frag = MainActivityListCellFragment.newInstance(list)
            supportFragmentManager.beginTransaction().add(R.id.main_activity_lists_frags,frag).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        menu?.findItem(R.id.master_list)?.setActionView(R.layout.master_item)

        val btn =
            menu?.findItem(R.id.master_list)?.actionView?.findViewById<ImageButton>(R.id.btn_master_list) as ImageButton

        btn.setOnClickListener {
            Intent(this, MasterListActivity::class.java).also {
                startActivity(it)
            }
        }

        return super.onCreateOptionsMenu(menu)
    }

    /**
     *  This method shows a PopupWindows with a calendar picker, and buttons for the user to select a date
     *  @param view is the view that will present the PopupWindow
     */

    private fun showThemePopUpView(){
        val inflater = layoutInflater
        //inflates the new_list_select_date_popup.xml layout
        val  popupView = inflater.inflate(R.layout.main_theme_chooser_popup, null)

        val width = LinearLayout.LayoutParams.WRAP_CONTENT
        val height = LinearLayout.LayoutParams.WRAP_CONTENT
        val focusable = true // allows the window to have focus

        // create the popup window
        val popupWindow = PopupWindow(popupView, width, height, focusable)

        val radioGroup = popupView.findViewById<RadioGroup>(R.id.radio_group_themes)

         fun getSelectedThemeRadio(): Int{
            when(getSharedPreferences(EXTRA.THEME, Context.MODE_PRIVATE).getInt(EXTRA.THEME_NAME, 1)){
                1 -> {return R.id.rb_1}
                2 -> {return R.id.rb_2}
                3 -> {return R.id.rb_3}
                4 -> {return R.id.rb_4}
                5 -> {return R.id.rb_5}
            }
             return R.id.rb_1
        }

        radioGroup.check(getSelectedThemeRadio())


        radioGroup.setOnCheckedChangeListener { _, id ->
            popupWindow.dismiss()
            when(id){
                R.id.rb_1 -> {setThemeColor(1)}
                R.id.rb_2 -> {setThemeColor(2)}
                R.id.rb_3 -> {setThemeColor(3)}
                R.id.rb_4 -> {setThemeColor(4)}
                R.id.rb_5 -> {setThemeColor(5)}
            }
        }

        // creates a shadow to simulate the view is higher than the background
        popupWindow.elevation = 20f

        //Renders the popupWindow centered at the view passed in the parameters
        popupWindow.showAtLocation(this.findViewById(R.id.main_activity_scrollview), Gravity.CENTER, 0, 0)


    }

    /**
     *  This method shows saves the Theme selected in the preferences and recreates the view
     *  @param theme is an Int representing the selected Theme
     */
   private fun setThemeColor(theme: Int){
        val preferences = getSharedPreferences(EXTRA.THEME, Context.MODE_PRIVATE)
        preferences.edit().putInt(EXTRA.THEME_NAME, theme).apply()
        recreate()

    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.master_list -> {
                Intent(this, MasterListActivity::class.java).also {
                    startActivity(it)
                }
            }
            R.id.theme_btn -> {
                showThemePopUpView()
            }

        }
        return super.onOptionsItemSelected(item)
    }
}