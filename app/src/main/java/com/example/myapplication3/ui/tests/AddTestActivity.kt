package com.example.myapplication3.ui.tests

import android.R
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication3.databinding.ActivityAddTestBinding
import com.example.myapplication3.model.response.tests.AddTestResponse
import com.example.myapplication3.model.response.users.AllUsersResponse
import com.example.myapplication3.model.response.users.DeleteUserResponse
import com.example.myapplication3.model.response.users.UpdateUserResponse
import com.example.myapplication3.network.apiclient.ApiClient
import com.example.myapplication3.ui.DashboardActivity
import com.example.myapplication3.ui.DashboardStaffActivity
import com.example.myapplication3.utils.CommonUtills
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.*

class AddTestActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddTestBinding;
    private lateinit var userBean: AllUsersResponse.UsersBean;
    private var type: String = "";
    var button_date: Button? = null
    var textview_date: TextView? = null
    var cal = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTestBinding.inflate(layoutInflater)
        val view = binding.root;
        setContentView(view)
        initData();
        textview_date = binding.edDate
        button_date = binding.edDateButton

        textview_date!!.text = "--/--/----"

        // create an OnDateSetListener
        val dateSetListener = object : DatePickerDialog.OnDateSetListener {
            override fun onDateSet(
                view: DatePicker, year: Int, monthOfYear: Int,
                dayOfMonth: Int
            ) {
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                updateDateInView()
            }
        }

        // when you click on the button, show DatePickerDialog that is set with OnDateSetListener
        button_date!!.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(this@AddTestActivity,
                    dateSetListener,
                    // set DatePickerDialog to point to today's date when it loads up
                    cal.get(Calendar.YEAR),
                    cal.get(Calendar.MONTH),
                    cal.get(Calendar.DAY_OF_MONTH)).show()
            }

        })
    }

    private fun initData(){
        CommonUtills.backHomeToolbar(binding.toolbarV1,"",this,false)
        getIntentData();
        clickListener();

    }

    private fun clickListener(){
        binding.btnAddNegativeTest.setOnClickListener {
            getInputs("negativ");
        }

        binding.btnPositiveTest.setOnClickListener {
            getInputs("pozitiv");
        }
    }



    private fun getInputs(result: String){
        val date = binding.edDate.text.toString();

        if(date.isNotBlank() && date.isNotEmpty() && type.isNotEmpty()){
            addTest(userBean.cnp, date, type, result)
        }else{
            showToast("All inputs required ... ");
        }

    }


    private fun getIntentData(){
        val intent = intent;
        if (intent.extras != null){
            userBean = intent!!.getSerializableExtra("user") as AllUsersResponse.UsersBean;
            autoFillData(userBean)
        }else{

        }
    }


    private fun autoFillData(userBean: AllUsersResponse.UsersBean){
        binding.edCNPTest.setText(userBean.cnp)

    }



    fun onRadioButtonClicked(view: View) {
        if (view is RadioButton) {
            // Is the button now checked?
            val checked = view.isChecked

            // Check which radio button was clicked
            when (view.getId()) {
                binding.rbPCR.id -> {
                    if (checked) {
                        type = "pcr"
                    }
                }
                binding.rbRapid.id -> {
                    if (checked) {
                        type = "rapid"
                    }
                }
                else -> {
                }


            }
        }

    }

    private fun addTest(cnp: String, date: String, type: String, result: String) {
        showToast(result)
        val apiCall = ApiClient.getService().addTest(cnp, result, type, date)
        apiCall.enqueue(object : Callback<AddTestResponse> {
            override fun onResponse(
                call: Call<AddTestResponse>,
                response: Response<AddTestResponse>
            ) {
                if (response.isSuccessful && response.body()!!.isSuccess == 1) {
                    showToast(response.body()!!.message)
                    Handler().postDelayed({
                        goToDashboard()
                    }, 1500)
                } else {
                    showToast(response.body()!!.message)
                }

            }

            override fun onFailure(call: Call<AddTestResponse>, t: Throwable) {
                Log.e(" fail ", " error " + t.localizedMessage)
                showToast("Server Error: " + t.localizedMessage)
            }
        })
    }

    private fun showToast( message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }



    private fun getDate(days: Int): String {
        var dt = Date()
        val c = Calendar.getInstance()
        c.time = dt
        c.add(Calendar.DATE, days)
        dt = c.time
        val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        val formattedText = sdf.format(dt);
        binding.edDate.setText(formattedText)
        return formattedText;
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

    private fun updateDateInView() {
        val myFormat = "MM/dd/yyyy" // mention the format you need
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        textview_date!!.text = sdf.format(cal.getTime())
    }

}