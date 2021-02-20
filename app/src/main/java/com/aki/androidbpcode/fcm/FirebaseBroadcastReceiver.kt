package com.aki.androidbpcode.fcm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.aki.androidbpcode.MainActivity
import com.aki.androidbpcode.R
import com.aki.commonlib.notification.NotificationUtils
import com.google.firebase.messaging.RemoteMessage

class FirebaseBroadcastReceiver : BroadcastReceiver() {
    private lateinit var context: Context
    private val TAG = this.javaClass.simpleName

    private val NOTIFICATION_ID = 123

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "common_channel_id"
        const val NOTIFICATION_CHANNEL_NAME = "common_channel_name"
    }

    override fun onReceive(context: Context, intent: Intent) {
        this.context = context
        val dataBundle = intent.extras
        if (dataBundle != null) {
            val remoteMessage = RemoteMessage(dataBundle)
            Log.d("MSG TYPE", "MSG TYPE >> " + remoteMessage.data["responseBody"])
            for (key in dataBundle.keySet()) {
                Log.d(TAG, "dataBundle Firebase: " + key + " value : " + dataBundle.get(key))
                handleFcm(remoteMessage)
            }
        }
    }

    private fun handleFcm(remoteMessage: RemoteMessage) {

        remoteMessage.data.let {
            val title = it["title"] ?: ""
            val msg = it["msg"] ?: ""
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            }

            NotificationUtils.createNotification(
                context,
                intent,
                title,
                msg,
                NOTIFICATION_ID,
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME
            )

        }

    }


}

