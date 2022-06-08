package com.example.myapplication3.ui.users

import android.R
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.example.myapplication3.network.apiclient.ApiClient
import com.example.myapplication3.databinding.ActivityNewUserBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*
import android.widget.RadioButton
import com.example.myapplication3.ui.DashboardActivity
import com.example.myapplication3.model.response.users.AddUserResponse
import com.example.myapplication3.utils.CommonUtills
import java.text.SimpleDateFormat


class NewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewUserBinding;

    private var expiryDate: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        val view = binding.root;
        setContentView(view)
        initData();
    }


    private fun initData(){
        CommonUtills.backHomeToolbar(binding.toolbarV1,"",this,false)

        binding.btnNewUser.setOnClickListener {
            getInputs();
        }

    }



    private fun getInputs(){
        val username = binding.edEmail.text.toString();
        val password = binding.edPassword.text.toString();

        if(username.isNotBlank() && password.isNotEmpty() ){
                addUser(username,password,expiryDate)
        }else{
            showToast("All inputs required ... ");
        }

    }

    private fun showToast( message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }


    private fun addUser(username: String, userPassword: String, userExpiry: String){
        showToast("Please wait ...")
        val apiCall = ApiClient.getService().addUser(username,userPassword,userExpiry)
        apiCall.enqueue(object : Callback<AddUserResponse>{
            override fun onResponse(
                call: Call<AddUserResponse>,
                response: Response<AddUserResponse>
            ) {
                if(response.isSuccessful && response.body()!!.isSuccess == 1){
                    showToast(response.body()!!.message)
                    Handler().postDelayed({
                        goToDashboard()
                    },1500)
                }else{
                    showToast(response.body()!!.message)
                }

            }

            override fun onFailure(call: Call<AddUserResponse>, t: Throwable) {
                showToast("Server Error: "+t.localizedMessage)
            }

        })

    }


    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                binding.rbOneDay.id -> {
                    if (checked) {
                        expiryDate = getExpiryDateTime(1)
                    }
                }
                binding.rbThreeDay.id -> {
                    if (checked) {
                        expiryDate = getExpiryDateTime(3)
                    }
                }
                binding.rbOneWeek.id -> {
                    if (checked) {
                        expiryDate = getExpiryDateTime(7)

                    }
                }
                binding.rbOneMonth.id -> {
                    if (checked) {
                        expiryDate = getExpiryDateTime(30)
                    }
                }
                else -> {
                }


            }
        }

    }


    private fun getExpiryDateTime(days: Int): String {
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, days)
        dt = c.time
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.format(dt);
    }

    private fun  goToDashboard(){
        startActivity(Intent(this, DashboardActivity::class.java))
        finish()
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