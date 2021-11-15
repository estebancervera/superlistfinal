package com.cerverae18.superlistfinal

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.cerverae18.superlistfinal.databinding.ActivityNewListBinding

class NewListActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewListBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}