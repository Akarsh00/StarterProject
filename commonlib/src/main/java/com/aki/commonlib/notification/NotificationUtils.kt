package com.aki.commonlib.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.aki.commonlib.R

object NotificationUtils {
    private lateinit var notificationManager: NotificationManager
    fun createNotification(
        context: Context,
        intent: Intent?,
        title: String,
        msg: String,
        notificationId: Int,
        notificationChannelId: String,
        notificationChannelName: String

    ) {
        notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        createNotificationChannelIfNeeded(notificationChannelId, notificationChannelName)


        val builder = if (intent != null) {
            val pendingIntent: PendingIntent =
                PendingIntent.getActivity(
                    context,
                    notificationId,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT
                )
            notificationWithPIntent(context, notificationChannelId, title, msg, pendingIntent)
        } else {
            notificationWithoutPIntent(context, notificationChannelId, title, msg)
        }
        notificationManager.notify(notificationId, builder)
    }

    private fun notificationWithPIntent(
        context: Context,
        notificationChannelId: String,
        title: String,
        msg: String,
        pendingIntent: PendingIntent
    ): Notification {
        return NotificationCompat.Builder(
            context,
            notificationChannelId
        ).setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)
            .setChannelId(notificationChannelId)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    private fun notificationWithoutPIntent(
        context: Context,
        notificationChannelId: String,
        title: String,
        msg: String
    ): Notification {
        return NotificationCompat.Builder(
            context,
            notificationChannelId
        ).setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle(title)
            .setContentText(msg)
            .setAutoCancel(true)
            .setChannelId(notificationChannelId)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .build()
    }

    private fun createNotificationChannelIfNeeded(
        notificationChannelId: String,
        notificationChannelName: String
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                notificationChannelId,
                notificationChannelName,
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}