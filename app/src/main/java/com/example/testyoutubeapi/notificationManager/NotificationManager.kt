package com.example.testyoutubeapi.notificationManager
import android.app.NotificationChannel
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import com.example.testyoutubeapi.constValues.CHANNEL_ID
import com.example.testyoutubeapi.constValues.CHANNEL_NAME

class NotificationManager(context: Context) {

    init {

    }

    val notificationManager = NotificationManagerCompat.from(context)

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }
    }
}