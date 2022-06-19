package com.example.myapplication3.ui.users

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication3.databinding.ActivityNewVisitorBinding
import com.example.myapplication3.model.response.auth.RegisterUserResponse
import com.example.myapplication3.network.apiclient.ApiClient
import com.example.myapplication3.ui.DashboardStaffActivity
import com.example.myapplication3.utils.CommonUtills
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewVisitorActivity : AppCompatActivity() {
    private lateinit var binding: ActivityNewVisitorBinding;


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNewVisitorBinding.inflate(layoutInflater)
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

        if(email.isNotBlank() && cnp.isNotBlank() && fullName.isNotBlank()){
            addUser(email, cnp, fullName)
        }else{
            showToast("All inputs required ... ");
        }

    }

    private fun showToast( message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }


    private fun addUser(email: String, cnp: String, fullName: String){
        showToast("Please wait ...")
        val apiCall = ApiClient.getService().registerUser(email, cnp, "visitor", fullName)
        apiCall.enqueue(object : Callback<RegisterUserResponse> {
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


    private fun  goToDashboard(){
        startActivity(Intent(this, DashboardStaffActivity::class.java))
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