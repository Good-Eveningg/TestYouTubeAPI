package com.example.testyoutubeapi.notificationManager

import android.content.Context
import androidx.core.app.NotificationCompat
import com.example.testyoutubeapi.R
import com.example.testyoutubeapi.constValues.CHANNEL_ID


class NotificationBuilder(context: Context) {
    val provideNotificationBuilder =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.internal_music_item)
            // Add media control buttons that invoke intents in your media service
            .addAction(R.drawable.back_button, "Previous", prevPendingIntent) // #0
            .addAction(R.drawable.pause_button, "Pause", pausePendingIntent) // #1
            .addAction(R.drawable.next_button, "Next", nextPendingIntent) // #2
            .setContentTitle("Wonderful music")
            .setContentText("My Awesome Band")
            .build()

}