package com.swancodes.mynotes.workers

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.swancodes.mynotes.R
import com.swancodes.mynotes.util.CHANNEL_ID
import com.swancodes.mynotes.util.CHANNEL_NAME
import com.swancodes.mynotes.util.NOTIFICATION_ID

@SuppressLint("MissingPermission")
fun showNotification(message: String, context: Context) {
    createNotificationChannel(context)

    // Create the notification
    val notification = NotificationCompat.Builder(context, CHANNEL_ID)
        .setContentTitle("Note Notification")
        .setContentText(message)
        .setSmallIcon(R.drawable.ic_notification)
        .setPriority(NotificationCompat.PRIORITY_HIGH)

    // Show the Notification
    with(NotificationManagerCompat.from(context)) {
        // NotificationId is a unique int for each notification that you must define
        notify(NOTIFICATION_ID, notification.build())
    }
}


private fun createNotificationChannel(context: Context) {
    // Create the Notification channel
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val channel = NotificationChannel(
            CHANNEL_ID, CHANNEL_NAME,
            NotificationManager.IMPORTANCE_DEFAULT
        )
        // Add the channel
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}