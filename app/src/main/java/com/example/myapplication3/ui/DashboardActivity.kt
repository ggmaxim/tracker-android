package com.example.myapplication3.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator
import com.example.myapplication3.adapter.AllUsersAdapter
import com.example.myapplication3.adapter.TabChoiceAdapter
import com.example.myapplication3.network.apiclient.ApiClient
import com.example.myapplication3.databinding.ActivityDashboardBinding
import com.example.myapplication3.model.response.users.AllUsersResponse
import com.example.myapplication3.ui.users.NewUserActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

import android.view.MenuItem
import android.R
import androidx.appcompat.widget.SearchView
import com.example.myapplication3.ui.users.EditUserActivity


class DashboardActivity : AppCompatActivity(), AllUsersAdapter.SelectedConsumer {

    private lateinit var binding: ActivityDashboardBinding;

    private lateinit var allUsersAdapter: AllUsersAdapter;

    private var adapter: TabChoiceAdapter? = null

    private lateinit var allUsersResponse: List<AllUsersResponse.UsersBean>;
    private lateinit var positiveUsersResponse: List<AllUsersResponse.UsersBean>;
    private lateinit var contactUsersResponse: List<AllUsersResponse.UsersBean>;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDashboardBinding.inflate(layoutInflater);
        val view = binding.root
        setContentView(view)
        initData();
    }

    private fun initData(){
        clickListener();
        setUpNewTrainerRecyclerView();
        getAllUsers();
        getPositiveUsers();
        getContactUsers();
        setTabData();
        setToolBar()
    }

    private fun setToolBar(){
        setSupportActionBar(binding.toolbarV1);
        supportActionBar!!.setDisplayShowTitleEnabled(false);
    }

    private fun setUpNewTrainerRecyclerView() {
        val layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = layoutManager
        allUsersAdapter = AllUsersAdapter(this)
        binding.recyclerView.adapter = allUsersAdapter
    }

    private fun clickListener(){
        binding.fabButton.setOnClickListener {
            showAddNewUser();
        }
    }

    private fun showAddNewUser(){
        startActivity(Intent(this, NewUserActivity::class.java))
    }

    override fun selectedAllConsumers(userBean: AllUsersResponse.UsersBean) {
        startActivity(Intent(this, EditUserActivity::class.java).putExtra("user",userBean))
    }

    private fun getAllUsers(){


        val apiCall = ApiClient.getService().getAllUsers( false, false);

        apiCall.enqueue(object : Callback<AllUsersResponse> {

            override fun onResponse(call: Call<AllUsersResponse>, response: Response<AllUsersResponse>) {
                if(response.isSuccessful && response.body()!!.isSuccess == 1){
                    if(response.body()!!.users.size > 0) {
                        allUsersResponse = response.body()!!.users;
                        allUsersAdapter.setItems(response.body()!!.users)
                    }else{
                        binding.tvNoUserFound.visibility = View.VISIBLE
                    }
                }else{
                    binding.tvNoUserFound.visibility = View.VISIBLE
                    showToast(response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<AllUsersResponse>, t: Throwable) {
                showToast("An error occurred: "+t.localizedMessage)
            }

        })

    }

    private fun getPositiveUsers(){


        val apiCall = ApiClient.getService().getAllUsers( true, false);

        apiCall.enqueue(object : Callback<AllUsersResponse> {

            override fun onResponse(call: Call<AllUsersResponse>, response: Response<AllUsersResponse>) {
                if(response.isSuccessful && response.body()!!.isSuccess == 1){
                    positiveUsersResponse = response.body()!!.users;
                }
            }

            override fun onFailure(call: Call<AllUsersResponse>, t: Throwable) {
                showToast("An error occurred: "+t.localizedMessage)
            }

        })

    }

    private fun getContactUsers() {
        val apiCall = ApiClient.getService().getAllUsers( false, true);

        apiCall.enqueue(object : Callback<AllUsersResponse> {

            override fun onResponse(call: Call<AllUsersResponse>, response: Response<AllUsersResponse>) {
                if(response.isSuccessful && response.body()!!.isSuccess == 1){
                    contactUsersResponse = response.body()!!.users;
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

    fun setTabData() {
        adapter = TabChoiceAdapter(this)
        binding.viewPager.offscreenPageLimit = 3
        binding.viewPager.adapter = adapter
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = "Users"
                1 -> tab.text = "COVID Positive"
                2 -> tab.text = "Contacte"
            }
        }.attach()
        binding.tabLayout.setOnTabSelectedListener(object : OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab) {
                when (tab.position) {
                    0 ->{
                        allUsersAdapter.setItems(filterAll())
                    }
                    1 ->{
                        allUsersAdapter.setItems(filterPositive())

                    }
                    2 ->{
                        allUsersAdapter.setItems(filterContact())

                    }
                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab) {}
            override fun onTabReselected(tab: TabLayout.Tab) {}
        })
    }

    private fun filterAll():List<AllUsersResponse.UsersBean>{
        getAllUsers()
        if (allUsersResponse.isNotEmpty()) {
            binding.tvNoUserFound.visibility = View.INVISIBLE
        } else {
            binding.tvNoUserFound.visibility = View.VISIBLE
        }
        return allUsersResponse;
    }

    private fun filterPositive():List<AllUsersResponse.UsersBean>{
        getPositiveUsers()
        if (positiveUsersResponse.isNotEmpty()) {
            binding.tvNoUserFound.visibility = View.INVISIBLE
        } else {
            binding.tvNoUserFound.visibility = View.VISIBLE
        }
        return positiveUsersResponse;
    }

    private fun filterContact():List<AllUsersResponse.UsersBean>{
        getContactUsers()
        if (contactUsersResponse.isNotEmpty()) {
            binding.tvNoUserFound.visibility = View.INVISIBLE
        } else {
            binding.tvNoUserFound.visibility = View.VISIBLE
        }
        return contactUsersResponse;
    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(com.example.myapplication3.R.menu.menu,menu);

        var menuItem = menu!!.findItem(com.example.myapplication3.R.id.searchView);

        var searchView = menuItem.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return  true;
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                allUsersAdapter.filter.filter(p0);
                return true
            }

        })



        return true;
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id: Int = item.itemId
        return if (id == com.example.myapplication3.R.id.searchView) {
            true
        } else super.onOptionsItemSelected(item)
    }



}