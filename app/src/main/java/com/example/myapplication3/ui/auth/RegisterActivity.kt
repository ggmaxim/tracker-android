package com.example.myapplication3.ui.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.example.myapplication3.network.apiclient.ApiClient
import com.example.myapplication3.databinding.ActivityRegisterBinding
import com.example.myapplication3.model.response.auth.RegisterUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response




class RegisterActivity : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        val view = binding.root;
        setContentView(view)
        initData();
    }


    private fun initData(){
        binding.llHaveAccount.setOnClickListener {
            loginUser();
        }

        binding.btnRegister.setOnClickListener {
            getInputs();
        }

    }

    private fun loginUser(){
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun getInputs(){
        val email = binding.edEmail.text.toString();
        val cnp = binding.edCNP.text.toString();
        val role = binding.edRole.text.toString();
        val full_name = binding.edFullName.text.toString();
        val password = binding.edPassword.text.toString()
        val cpassword = binding.edConfirmPassword.text.toString()

        if(cnp.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty() && cpassword.isNotEmpty() ){
            if(password == cpassword){
                registerUser(email,password, cnp, role, full_name)
            }else{
                showToast("Password and Confirm Password should be the same")
            }

        }else{
            showToast("All inputs required ... ");
        }

    }

    private fun showToast( message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }


    private fun registerUser(userEmail: String, userPassword: String,
                             cnp: String, role: String, full_name: String){
        showToast("Registering, Please wait ...")
        val apiCall = ApiClient.getService().registerUser(userEmail,userPassword, cnp, role, full_name)
        apiCall.enqueue(object : Callback<RegisterUserResponse>{
            override fun onResponse(
                call: Call<RegisterUserResponse>,
                response: Response<RegisterUserResponse>
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

            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
                Log.e(" fail "," error "+t.localizedMessage)
               showToast("Server Error: "+t.localizedMessage)
            }

        })

    }



}