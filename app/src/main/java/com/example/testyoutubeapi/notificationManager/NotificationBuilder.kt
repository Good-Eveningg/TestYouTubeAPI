package com.example.testyoutubeapi.notificationManager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import com.example.testyoutubeapi.R
import com.example.testyoutubeapi.bcReceiver.MyReceiver
import com.example.testyoutubeapi.constValues.*


class NotificationBuilder(context: Context) {
    val mediSessionCompat = MediaSessionCompat(context, "tag")
    val intent = Intent(context, MyReceiver::class.java).apply {
        putExtra(BACK_BUTTON, CLICKED)
        putExtra(PLAY_PAUSE_BUTTON, CLICKED)
        putExtra(NEXT_BUTTON, CLICKED)
    }
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE
    } else {
        0
    }
    val prevPendingIntent = PendingIntent.getBroadcast(context, 0, intent, flag)
    val pausePendingIntent = PendingIntent.getBroadcast(context, 1, intent, flag)
    val nextPendingIntent = PendingIntent.getBroadcast(context, 2, intent, flag)

    val provideNotificationBuilder =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.internal_music_item)
            .addAction(R.drawable.back_button, "Previous", prevPendingIntent) // #0
            .addAction(R.drawable.pause_button, "PlayPause", pausePendingIntent) // #1
            .addAction(R.drawable.next_button, "Next", nextPendingIntent) // #2
            .setContentTitle("Wonderful music")
            .setContentText("My Awesome Band")
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mediSessionCompat.sessionToken)
            )
            .build()


}