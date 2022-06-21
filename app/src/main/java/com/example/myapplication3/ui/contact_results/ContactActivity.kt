package com.example.myapplication3.ui.contact_results

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication3.databinding.ActivityContactBinding
import com.example.myapplication3.ui.DashboardActivity
import com.example.myapplication3.utils.CommonUtills

class ContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityContactBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactBinding.inflate(layoutInflater)
        val view = binding.root;
        setContentView(view)
        initData();
    }

    private fun initData(){
        CommonUtills.backHomeToolbar(binding.toolbarV1,"",this,false)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}