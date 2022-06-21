package com.example.myapplication3.notifications

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import com.example.myapplication3.R
import com.example.myapplication3.ui.MainActivity
import com.example.myapplication3.ui.auth.LoginActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


const val channelId = "notification_channel"
const val channelName = "com.example.myapplication3"

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        Log.d("tag", "Refreshed token: $token")

        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d("tag", "From: ${remoteMessage.from}")
        println("message receiver!!!!!!!!!!!!!!!!!");
        if(remoteMessage.getNotification() !== null) {
            generateNotification(remoteMessage.notification!!.title!!, remoteMessage.notification!!.body!!)
        }

    }

    fun getRemoveView (title: String, message: String): RemoteViews{
        val remoteView = RemoteViews("com.example.myapplication3", R.layout.activity_notification)
        remoteView.setTextViewText(R.id.title, title)
        remoteView.setTextViewText(R.id.description, message)
        remoteView.setImageViewResource(R.id.app_notification, R.drawable.button_selector)

        return remoteView;
    }

    fun generateNotification(title: String, message: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT)

        // channel id, channel name
        var builder: NotificationCompat.Builder = NotificationCompat.Builder(applicationContext, channelId)
            .setSmallIcon(R.drawable.button_selector)
            .setAutoCancel(true)
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000))
            .setOnlyAlertOnce(true)
            .setContentIntent(pendingIntent)

        builder = builder.setContent(getRemoveView(title, message))

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notificationChannel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH)
        notificationManager.createNotificationChannel(notificationChannel)

        notificationManager.notify(0, builder.build())

    }

//    private val TAG = "FireBaseMessagingService"
//    var NOTIFICATION_CHANNEL_ID = "net.larntech.notification"
//    val NOTIFICATION_ID = 100
//
//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
//
//        Log.e("message","Message Received ...");
//
//        if (remoteMessage.data.size > 0) {
//            val title = remoteMessage.data["title"]
//            val body = remoteMessage.data["body"]
//            showNotification(applicationContext, title, body)
//        } else {
//            val title = remoteMessage.notification!!.title
//            val body = remoteMessage.notification!!.body
//            showNotification(applicationContext, title, body)
//        }
//    }
//
//
//
//    fun showNotification(
//        context: Context,
//        title: String?,
//        message: String?
//    ) {
//        val ii: Intent
//        ii = Intent(context, MainActivity::class.java)
//        ii.data = Uri.parse("custom://" + System.currentTimeMillis())
//        ii.action = "actionstring" + System.currentTimeMillis()
//        ii.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
//        val pi =
//            PendingIntent.getActivity(context, 0, ii, PendingIntent.FLAG_UPDATE_CURRENT)
//        val notification: Notification
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            //Log.e("Notification", "Created in up to orio OS device");
//            notification = NotificationCompat.Builder(context, NOTIFICATION_CHANNEL_ID)
//                .setOngoing(true)
//                .setSmallIcon(getNotificationIcon())
//                .setContentText(message)
//                .setAutoCancel(true)
//                .setContentIntent(pi)
//                .setPriority(NotificationCompat.PRIORITY_HIGH)
//                .setCategory(Notification.CATEGORY_SERVICE)
//                .setWhen(System.currentTimeMillis())
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setContentTitle(title).build()
//            val notificationManager = context.getSystemService(
//                Context.NOTIFICATION_SERVICE
//            ) as NotificationManager
//            val notificationChannel = NotificationChannel(
//                NOTIFICATION_CHANNEL_ID,
//                title,
//                NotificationManager.IMPORTANCE_DEFAULT
//            )
//            notificationManager.createNotificationChannel(notificationChannel)
//            notificationManager.notify(NOTIFICATION_ID, notification)
//        } else {
//            notification = NotificationCompat.Builder(context)
//                .setSmallIcon(getNotificationIcon())
//                .setAutoCancel(true)
//                .setContentText(message)
//                .setContentIntent(pi)
//                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
//                .setContentTitle(title).build()
//            val notificationManager = context.getSystemService(
//                Context.NOTIFICATION_SERVICE
//            ) as NotificationManager
//            notificationManager.notify(NOTIFICATION_ID, notification)
//        }
//    }
//
//    private fun getNotificationIcon(): Int {
//        val useWhiteIcon =
//            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
//        return if (useWhiteIcon) R.mipmap.ic_launcher else R.mipmap.ic_launcher
//    }

}