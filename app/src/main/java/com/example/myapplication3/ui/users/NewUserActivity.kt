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
import com.example.myapplication3.model.response.auth.RegisterUserResponse
import com.example.myapplication3.ui.DashboardActivity
import com.example.myapplication3.model.response.users.AddUserResponse
import com.example.myapplication3.utils.CommonUtills
import java.text.SimpleDateFormat


class NewUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNewUserBinding;

    private var role: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewUserBinding.inflate(layoutInflater)
        val view = binding.root;
        setContentView(view)
        initData();
    }


    private fun initData(){
        CommonUtills.backHomeToolbar(binding.toolbarV1,"",this,false)
        binding.btnRegister.setOnClickListener {
            getInputs();
        }

    }



    private fun getInputs(){
        val email = binding.edEmail.text.toString();
        val cnp = binding.edCNP.text.toString();
        val fullName = binding.edFullName.text.toString();

        if(email.isNotBlank() && cnp.isNotBlank() && fullName.isNotBlank() && role.isNotEmpty()){
                addUser(email,cnp, fullName, role)
        }else{
            showToast("All inputs required ... ");
        }

    }

    private fun showToast( message: String){
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show()
    }


    private fun addUser(email: String, cnp: String, fullName: String, role: String){
        showToast("Please wait ...")
        val apiCall = ApiClient.getService().registerUser(email, cnp, role, fullName)
        apiCall.enqueue(object : Callback<RegisterUserResponse>{
            override fun onResponse(
                call: Call<RegisterUserResponse>,
                response: Response<RegisterUserResponse>
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

            override fun onFailure(call: Call<RegisterUserResponse>, t: Throwable) {
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
                binding.rbAdmin.id -> {
                    if (checked) {
                        role = "admin"
                    }
                }
                binding.rbStaff.id -> {
                    if (checked) {
                        role = "staff"
                    }
                }
                binding.rbVisitor.id -> {
                    if (checked) {
                        role = "visitor"

                    }
                }
                else -> {
                }


            }
        }

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