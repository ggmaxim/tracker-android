package com.example.myapplication3.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myapplication3.adapter.AllUsersAdapter
import com.example.myapplication3.adapter.TabChoiceAdapter
import com.example.myapplication3.databinding.ActivityDashboardBinding
import com.example.myapplication3.databinding.ActivityTemporaryDashboardBinding
import com.example.myapplication3.model.response.users.AllUsersResponse
import com.example.myapplication3.network.apiclient.ApiClient
import com.example.myapplication3.ui.location.LocationProvider
import com.example.myapplication3.ui.tests.AddTestActivity
import com.example.myapplication3.ui.users.NewUserActivity
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DashboardTemporaryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTemporaryDashboardBinding;
    private lateinit var b: Bundle

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTemporaryDashboardBinding.inflate(layoutInflater);
        val view = binding.root
        val i:Intent = intent
        b = i.extras!!
        setContentView(view)
        initData();
    }

    private fun initData(){
        clickListener();
    }



    private fun clickListener(){
        binding.btnStartVisit.setOnClickListener {
//            startLocationActivity();
            startLocationActivity(b.get("id") as String);
        }
    }

    private fun startLocationActivity (id: String) {
        startActivity(Intent(this, LocationProvider::class.java).putExtra("id", id))
    }



}