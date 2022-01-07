package com.example.workmanager.workers

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.workmanager.R

const val CHANNEL_ID = "WORKMANAGER_NOTIFICATION"

fun makeStatusNotification(id:Int, message: String, context: Context) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        val name = "MyChannelName"
        val description_text = "NotificationChannel description"
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(CHANNEL_ID, name, importance).apply{
            description=description_text
        }
        // Register the channel with the system
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(notificationChannel)
    }
    // Create the notification
    val builder = NotificationCompat.Builder(context, CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_launcher_foreground)
        .setContentText(message)
        .setPriority(NotificationCompat.PRIORITY_HIGH)
    // Send the notification
    // notificationId is a unique int for each notification that you must define
    val notificationId = id
    NotificationManagerCompat.from(context).notify(notificationId, builder.build())
}