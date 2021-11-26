package com.cerverae18.superlistfinal

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.viewModels
import com.cerverae18.superlistfinal.databinding.ActivityMainBinding
import com.cerverae18.superlistfinal.logic.entities.List
import com.cerverae18.superlistfinal.fragments.MainActivityListCellFragment

import com.cerverae18.superlistfinal.logic.ListViewModel
import com.cerverae18.superlistfinal.logic.ListViewModelFactory


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    val listViewModel: ListViewModel by viewModels {
        ListViewModelFactory((application as GeneralApplication).listRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

       // this.supportActionBar?.setDisplayShowTitleEnabled(false)

        this.supportActionBar?.title = getString(R.string.lists_title)

        listViewModel.allLists.observe(this, { lists ->
            setupListFrags(lists)

        })

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