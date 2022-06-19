package com.example.myapplication3.ui.users

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication3.databinding.ActivityEditUserBinding
//import com.example.myapplication3.databinding.ActivityEditUserBinding
import com.example.myapplication3.model.response.users.AllUsersResponse
import com.example.myapplication3.model.response.users.DeleteUserResponse
import com.example.myapplication3.model.response.users.UpdateUserResponse
import com.example.myapplication3.network.apiclient.ApiClient
import com.example.myapplication3.ui.DashboardActivity
import com.example.myapplication3.utils.CommonUtills
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditUserActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEditUserBinding;
    private lateinit var userBean: AllUsersResponse.UsersBean
    private var role: String = "";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditUserBinding.inflate(layoutInflater)
        val view = binding.root;
        setContentView(view)
        initData();
    }

    private fun initData(){
        CommonUtills.backHomeToolbar(binding.toolbarV1,"",this,false)
        getIntentData();
        clickListener();

    }

    private fun clickListener(){
        binding.btnEdit.setOnClickListener {
            getInputs();
        }

        binding.btnDelete.setOnClickListener {
            deleteUser(userBean.id);
        }
    }



    private fun getInputs(){
        val email = binding.edEmail.text.toString();
        val cnp = binding.edCNP.text.toString();
        val fullName = binding.edFullName.text.toString()

        if(email.isNotBlank() && cnp.isNotEmpty() && fullName.isNotEmpty() && role.isNotEmpty()){
            updateUser(userBean.id, email, cnp, fullName, role)
        }else{
            showToast("All inputs required ... ");
        }

    }


    private fun getIntentData(){
        val intent = intent;
        if(intent.extras != null){
            userBean = intent!!.getSerializableExtra("user") as AllUsersResponse.UsersBean;
            autoFillData(userBean)
        }else{

        }
    }


    private fun autoFillData(userBean: AllUsersResponse.UsersBean){
        binding.edEmail.setText(userBean.email)
        binding.edCNP.setText(userBean.cnp)
        binding.edFullName.setText(userBean.fullName)
        if (userBean.role == "admin") {
            binding.rbAdmin.isChecked = true;
        } else if (userBean.role == "staff") {
            binding.rbStaff.isChecked = true;
        } else {
            binding.rbVisitor.isChecked = true;
        }
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


    private fun updateUser(id: String, email: String, cnp: String, fullName: String, role: String){
        showToast("Updating user ...")
        val apiCall = ApiClient.getService().updateUser(id, email, cnp, fullName, role)
        apiCall.enqueue(object : Callback<UpdateUserResponse> {
            override fun onResponse(
                call: Call<UpdateUserResponse>,
                response: Response<UpdateUserResponse>
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

            override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
                Log.e(" fail "," error "+t.localizedMessage)
                showToast("Server Error: "+t.localizedMessage)
            }

        })

    }




    private fun deleteUser(client_id: String){
        showToast("Deleting user ...")
        val apiCall = ApiClient.getService().deleteUser(client_id)
        apiCall.enqueue(object : Callback<DeleteUserResponse> {
            override fun onResponse(
                call: Call<DeleteUserResponse>,
                response: Response<DeleteUserResponse>
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

            override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
                Log.e(" fail "," error "+t.localizedMessage)
                showToast("Server Error: "+t.localizedMessage)
            }

        })

    }



    private fun showToast( message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
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