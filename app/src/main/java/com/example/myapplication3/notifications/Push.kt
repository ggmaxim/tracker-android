package com.example.myapplication3.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.util.Log

//import android.util.Log
import androidx.core.app.NotificationManagerCompat
import com.example.myapplication3.R
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.google.firebase.messaging.ktx.messaging

class Push : FirebaseMessagingService() {
//    override fun onCreate() {
//        Firebase.messaging.subscribeToTopic("topic")
//            .addOnCompleteListener { task ->
//                var msg = "Subscribed"
//                if (!task.isSuccessful) {
//                    msg = "Subscribe failed"
//                }
//            }
//        super.onCreate()
//    }
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        val title = remoteMessage.notification!!.title
        val text = remoteMessage.notification!!.body
        val CHANNEL_ID = "HEADS_UP_NOTIFICATION"
        val channel = NotificationChannel(
            CHANNEL_ID,
            "Heads Up Notification",
            NotificationManager.IMPORTANCE_HIGH
        )
        val notification = Notification.Builder(this, CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
        NotificationManagerCompat.from(this).notify(1, notification.build())

        super.onMessageReceived(remoteMessage)
    }

//    override fun onNewToken(token: String) {
//        Log.d("fire", "Token: $token");
//        super.onNewToken(token)
//    }

}