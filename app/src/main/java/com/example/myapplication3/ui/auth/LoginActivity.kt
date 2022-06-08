package com.example.myapplication3.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.myapplication3.ui.DashboardActivity
import com.example.myapplication3.network.apiclient.ApiClient
import com.example.myapplication3.databinding.ActivityLoginBinding
import com.example.myapplication3.model.response.auth.LoginResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        var view = binding.root;
        setContentView(view)
        initData()

    }

    private fun initData(){
        binding.llNoAccount.setOnClickListener {
            registerUser();
        }
        binding.btnLogin.setOnClickListener {
            getInputs();
        }
    }

    private fun registerUser(){
        startActivity(Intent(this, RegisterActivity::class.java))
        finish()
    }

    private fun getInputs(){
        val email = binding.edEmail.text.toString();
        val password = binding.edPassword.text.toString();

        if(email.isNotEmpty() && password.isNotEmpty()){
            authUser(email,password)
        }else{
            showToast("All inputs required ...");
        }

    }

    private fun showToast( message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    private fun authUser(email:String, password: String){
        showToast("Authentication, Please wait ...")
        val apiCall = ApiClient.getService().authenticateUser(email,password);
        apiCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if(response.isSuccessful && response.body()!!.isSuccess == 1) {
                        startDashboardActivity(response.body()!!.email)
                }else {
                    showToast(response.body()!!.message)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                showToast("An error occurred: "+t.localizedMessage)
            }

        })

    }

    private fun startDashboardActivity(email: String){
        startActivity(Intent(this, DashboardActivity::class.java).putExtra("data",email))
        finish()
    }

}