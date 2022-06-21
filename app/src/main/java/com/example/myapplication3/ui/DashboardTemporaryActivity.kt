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
import com.example.myapplication3.ui.contact_results.ContactActivity
import com.example.myapplication3.ui.contact_results.NotContactActivity
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
    private lateinit var b: Bundle;

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
            startLocationActivity(b.get("id") as String);
        }

        binding.btnCheckContact.setOnClickListener {
            getContacts(b.get("id") as String);
        }
    }

    private fun startLocationActivity (id: String) {
        startActivity(Intent(this, LocationProvider::class.java).putExtra("id", id))
    }

    private fun startContactActivity (id: String) {
        startActivity(Intent(this, ContactActivity::class.java).putExtra("id", id))
    }

    private fun startNotContactActivity (id: String) {
        startActivity(Intent(this, NotContactActivity::class.java).putExtra("id", id))
    }

    private fun getContacts (id: String) {
        val apiCall = ApiClient.getService().getAllUsers( false, true);

        apiCall.enqueue(object : Callback<AllUsersResponse> {

            override fun onResponse(call: Call<AllUsersResponse>, response: Response<AllUsersResponse>) {
                if(response.isSuccessful && response.body()!!.isSuccess == 1){
                    val contactUsersResponse = response.body()!!.users;
                    if (contactUsersResponse.any{it.id == id }) {
                        startContactActivity(id);

                    } else {
                        startNotContactActivity(id);
                    }
                }
            }

            override fun onFailure(call: Call<AllUsersResponse>, t: Throwable) {
                showToast("An error occurred: "+t.localizedMessage)
            }

        })
    }
    private fun showToast( message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }


}