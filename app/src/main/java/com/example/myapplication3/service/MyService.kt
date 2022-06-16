package com.example.myapplication3.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import java.util.logging.Handler

class MyService: Service() {
    private lateinit var mHandler: Handler
    private lateinit var mRunnable: Runnable

    override fun onBind(intent: Intent): IBinder? {
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        // Send a notification that service is started
//        toast("Service started.")
//
//        // Do a periodic task
//        mHandler = Handler()
//        mRunnable = Runnable { showRandomNumber() }
//        mHandler.postDelayed(mRunnable, 5000)
//
        return Service.START_STICKY
    }

    override fun onDestroy() {
//        super.onDestroy()
//        toast("Service destroyed.")
//        mHandler.removeCallbacks(mRunnable)
    }

    // Custom method to do a task
    private fun showRandomNumber() {
//        val rand = Random()
//        val number = rand.nextInt(100)
//        toast("Random Number : $number")
//        mHandler.postDelayed(mRunnable, 5000)
    }
}