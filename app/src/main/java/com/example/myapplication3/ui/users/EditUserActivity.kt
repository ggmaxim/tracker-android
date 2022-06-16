package com.example.myapplication3.ui.users

import android.R
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

//    private lateinit var binding: ActivityEditUserBinding;
//    private lateinit var userBean: AllUsersResponse.UsersBean
//    private var expiryDate: String = "";
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivityEditUserBinding.inflate(layoutInflater)
//        val view = binding.root;
//        setContentView(view)
//        initData();
//    }
//
//    private fun initData(){
//        CommonUtills.backHomeToolbar(binding.toolbarV1,"",this,false)
//        getIntentData();
//        clickListener();
//
//    }
//
//    private fun clickListener(){
////        binding.btnUpdateUser.setOnClickListener {
////            getInputs();
////        }
////
////        binding.btnDeleteUser.setOnClickListener {
////            deleteUser(userBean.id);
////        }
//    }
//
//
//
//    private fun getInputs(){
////        val username = binding.edEmail.text.toString();
////        val password = binding.edPassword.text.toString();
////        val deviceId = binding.edDeviceId.text.toString()
////        val expiryDate = binding.edExpiryDate.text.toString()
////
////        if(username.isNotBlank() && password.isNotEmpty() ){
////            updateUser(userBean.id,username,password,deviceId,expiryDate)
////        }else{
////            showToast("All inputs required ... ");
////        }
//
//    }
//
//
//    private fun getIntentData(){
//        val intent = intent;
//        if(intent.extras != null){
//            userBean = intent!!.getSerializableExtra("user") as AllUsersResponse.UsersBean;
//            autoFillData(userBean)
//        }else{
//
//        }
//    }
//
//
//    private fun autoFillData(userBean: AllUsersResponse.UsersBean){
////        binding.edEmail.setText(userBean.email)
////        binding.edCNP.setText(userBean.expiry)
////        binding.edPassword.setText(userBean.password)
////        binding.edDeviceId.setText(userBean.deviceId)
//
//    }
//
//
//
//    fun onRadioButtonClicked(view: View) {
////        if (view is RadioButton) {
////            // Is the button now checked?
////            val checked = view.isChecked
////
////            // Check which radio button was clicked
////            when (view.getId()) {
////                binding.rbOneDay.id -> {
////                    if (checked) {
////                        expiryDate = getExpiryDateTime(1)
////                    }
////                }
////                binding.rbThreeDay.id -> {
////                    if (checked) {
////                        expiryDate = getExpiryDateTime(3)
////                    }
////                }
////                binding.rbOneWeek.id -> {
////                    if (checked) {
////                        expiryDate = getExpiryDateTime(7)
////
////                    }
////                }
////                binding.rbOneMonth.id -> {
////                    if (checked) {
////                        expiryDate = getExpiryDateTime(30)
////                    }
////                }
////                else -> {
////                }
////
////
////            }
////        }
//
//    }
//
//
//    private fun updateUser(id: String, username: String, userPassword: String, deviceId: String, userExpiry: String){
//        showToast("Updating user ...")
//        val apiCall = ApiClient.getService().updateUser(id,username,userPassword,deviceId,userExpiry)
//        apiCall.enqueue(object : Callback<UpdateUserResponse> {
//            override fun onResponse(
//                call: Call<UpdateUserResponse>,
//                response: Response<UpdateUserResponse>
//            ) {
//                if(response.isSuccessful && response.body()!!.isSuccess == 1){
//                    showToast(response.body()!!.message)
//                    Handler().postDelayed({
//                        goToDashboard()
//                    },1500)
//                }else{
//                    showToast(response.body()!!.message)
//                }
//
//            }
//
//            override fun onFailure(call: Call<UpdateUserResponse>, t: Throwable) {
//                Log.e(" fail "," error "+t.localizedMessage)
//                showToast("Server Error: "+t.localizedMessage)
//            }
//
//        })
//
//    }
//
//
//
//
//    private fun deleteUser(id: String){
//        showToast("Deleting user ...")
//        val apiCall = ApiClient.getService().deleteUser(id)
//        apiCall.enqueue(object : Callback<DeleteUserResponse> {
//            override fun onResponse(
//                call: Call<DeleteUserResponse>,
//                response: Response<DeleteUserResponse>
//            ) {
//                if(response.isSuccessful && response.body()!!.isSuccess == 1){
//                    showToast(response.body()!!.message)
//                    Handler().postDelayed({
//                        goToDashboard()
//                    },1500)
//                }else{
//                    showToast(response.body()!!.message)
//                }
//
//            }
//
//            override fun onFailure(call: Call<DeleteUserResponse>, t: Throwable) {
//                Log.e(" fail "," error "+t.localizedMessage)
//                showToast("Server Error: "+t.localizedMessage)
//            }
//
//        })
//
//    }
//
//
//
//    private fun showToast( message: String){
//        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
//    }
//
//
//
//    private fun getExpiryDateTime(days: Int): String {
////        var dt = Date()
////        val c = Calendar.getInstance()
////        c.time = dt
////        c.add(Calendar.DATE, days)
////        dt = c.time
////        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
////        val formattedText = sdf.format(dt);
////        binding.edExpiryDate.setText(formattedText)
////        return formattedText;
//        return "";
//    }
//
//    private fun  goToDashboard(){
//        startActivity(Intent(this, DashboardActivity::class.java))
//        finish()
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.home -> {
//                finish()
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

}