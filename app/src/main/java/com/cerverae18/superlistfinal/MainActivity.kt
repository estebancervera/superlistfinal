package com.cerverae18.superlistfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.cerverae18.superlistfinal.databinding.ActivityMainBinding
import java.sql.Date
import java.util.*
import kotlin.collections.HashMap

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        this.supportActionBar?.setDisplayShowTitleEnabled(false)

        val lists = mutableListOf<List>(List("SORIANA", Date(Calendar.getInstance().timeInMillis), HashMap<Product, Int>()),
            List("LUNES", Date(Calendar.getInstance().timeInMillis), HashMap<Product, Int>()),
            List(
                "WALLMART",
                Date(Calendar.getInstance().timeInMillis),
                HashMap<Product, Int>()
            ),
            List(
                "CENA",
                Date(Calendar.getInstance().timeInMillis),
                HashMap<Product, Int>()
            )
        )




        for(i in 1..20){
            val frag = MainActivityListCellFragment.newInstance(lists.first())
            supportFragmentManager.beginTransaction().add(R.id.main_activity_lists_frags, frag).commit()
        }



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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.master_list -> {
                Intent(this, MasterListActivity::class.java).also {
                    startActivity(it)
                }
            }

        }
        return super.onOptionsItemSelected(item)
    }
}