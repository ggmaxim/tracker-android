package com.example.myapplication3.ui.location

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.myapplication3.R
import com.example.myapplication3.databinding.ActivityLocationProviderBinding
import com.example.myapplication3.model.response.auth.RegisterUserResponse
import com.example.myapplication3.network.apiclient.ApiClient
import com.example.myapplication3.ui.auth.LoginActivity
import com.google.android.gms.location.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import android.provider.Settings
import android.view.View
import androidx.core.app.ActivityCompat
import com.example.myapplication3.model.response.location.AddLocationResponse
import com.example.myapplication3.model.response.tests.AddTestResponse
import com.example.myapplication3.utils.PermissionUtils

class LocationProvider : AppCompatActivity() {

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 999
    }
    private lateinit var fusedLocationClient: FusedLocationProviderClient;
    private lateinit var binding: ActivityLocationProviderBinding;
    private lateinit var locationCallback: LocationCallback;

    private lateinit var b: Bundle

    private var latitude: Double = 0.0
    private var longitude: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationProviderBinding.inflate(layoutInflater)
        val view = binding.root
        val i:Intent = intent
        b = i.extras!!
        setContentView(view)
        clickListener();
    }

    private fun clickListener(){
        binding.btnStopVisit.setOnClickListener {
            stopLocationUpdates();
        }
    }


    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
    }


    private fun setUpLocationListener() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                for (location in locationResult.locations) {
                    binding.latTextView.text = location.latitude.toString()
                    binding.lngTextView.text = location.longitude.toString()
                    addLocation(b.get("id") as String, location.latitude, location.longitude)
                }
                // Few more things we can do here:
                // For example: Update the location of user on server
            }
        }
        val fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this)
        // for getting the current location update after every 2 seconds with high accuracy
        val locationRequest = LocationRequest().setInterval(2000).setFastestInterval(2000)
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.myLooper()
        )
    }

    override fun onStart() {
        super.onStart()
        when {
            PermissionUtils.isAccessFineLocationGranted(this) -> {
                when {
                    PermissionUtils.isLocationEnabled(this) -> {
                        setUpLocationListener()
                    }
                    else -> {
                        PermissionUtils.showGPSNotEnabledDialog(this)
                    }
                }
            }
            else -> {
                PermissionUtils.requestAccessFineLocationPermission(
                    this,
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
        }
    }

    private fun addLocation (id: String, latitude: Number, longitude: Number) {
        val apiCall = ApiClient.getService().addLocation(id, latitude, longitude)
        apiCall.enqueue(object : Callback<AddLocationResponse> {
            override fun onResponse(
                call: Call<AddLocationResponse>,
                response: Response<AddLocationResponse>
            ) {
                if (response.isSuccessful && response.body()!!.isSuccess == 1) {

                } else {
                    showToast(response.body()!!.message)
                }

            }

            override fun onFailure(call: Call<AddLocationResponse>, t: Throwable) {
                Log.e(" fail ", " error " + t.localizedMessage)
                showToast("Server Error: " + t.localizedMessage)
            }
        })
    }

    private fun showToast( message: String){
        Toast.makeText(this,message, Toast.LENGTH_SHORT).show()
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            LOCATION_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    when {
                        PermissionUtils.isLocationEnabled(this) -> {
                            setUpLocationListener()
                        }
                        else -> {
                            PermissionUtils.showGPSNotEnabledDialog(this)
                        }
                    }
                } else {
                    Toast.makeText(
                        this,
                        getString(R.string.location_permission_not_granted),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }
    }
}