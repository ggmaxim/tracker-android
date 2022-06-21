package com.example.myapplication3.ui.contact_results

import android.R
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication3.databinding.ActivityNotContactBinding
import com.example.myapplication3.utils.CommonUtills

class NotContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNotContactBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotContactBinding.inflate(layoutInflater)
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