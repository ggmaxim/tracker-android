package com.example.myapplication3.ui.auth

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication3.databinding.ActivityChangePasswordBinding
import com.example.myapplication3.model.response.auth.LoginResponse
import com.example.myapplication3.model.response.users.UpdateUserResponse
import com.example.myapplication3.network.apiclient.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChangePasswordBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        val view = binding.root;
        setContentView(view)
        initData();
    }


    private fun initData(){
        binding.llHaveAccount.setOnClickListener {
            loginUser();
        }

        binding.btnChangePassword.setOnClickListener {
            getInputs();
        }

    }

    private fun loginUser(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun getInputs(){
        val email = binding.edEmail.text.toString();
        val oldPassword = binding.edOldPassword.text.toString();
        val password = binding.edPassword.text.toString()
        val cpassword = binding.edConfirmPassword.text.toString()

        if(oldPassword.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty() ){
            if(password == cpassword){
                changePassword(email, oldPassword, password)
            }else{
                showToast("Password and Confirm Password should be the same")
            }

        }else{
            showToast("All inputs required ... ");
        }

    }

    private fun showToast( message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    private fun updatePassword (user_id: String, password: String) {
        val apiCall = ApiClient.getService().updatePassword(user_id, password)
        apiCall.enqueue(object : Callback<UpdateUserResponse> {
            override fun onResponse(
                call: Call<UpdateUserResponse>,
                response: Response<UpdateUserResponse>
            ) {
                if(response.isSuccessful && response.body()!!.isSuccess == 1){

                    showToast(response.body()!!.message)
                    Handler().postDelayed({
                        loginUser()
                    },1500)
                }else{
                    showToast(response.body()!!.message)
                }

            }

            override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                Log.e(" fail "," error "+t.localizedMessage)
                showToast("Server Error: "+t.localizedMessage)
            }

        })
    }

    private fun changePassword (email: String, oldPassword: String, password: String) {
        showToast("Please wait ...")
        val apiCall = ApiClient.getService().authenticateUser(email, oldPassword)
        apiCall.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(
                call: Call<LoginResponse>,
                response: Response<LoginResponse>
            ) {
                if(response.isSuccessful && response.body()!!.isSuccess == 1){
                    updatePassword(response.body()!!.id, password)
                    showToast(response.body()!!.message)
                    Handler().postDelayed({
                        loginUser()
                    },1500)
                }else{
                    showToast(response.body()!!.message)
                }

            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e(" fail "," error "+t.localizedMessage)
                showToast("Server Error: "+t.localizedMessage)
            }

        })
    }

}