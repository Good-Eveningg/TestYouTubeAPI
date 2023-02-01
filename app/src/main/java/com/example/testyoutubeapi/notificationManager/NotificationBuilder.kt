package com.example.testyoutubeapi.notificationManager

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.app.NotificationCompat
import androidx.lifecycle.MutableLiveData
import com.example.testyoutubeapi.R
import com.example.testyoutubeapi.bcReceiver.MyReceiver
import com.example.testyoutubeapi.bcReceiver.constValues.BACK_BUTTON
import com.example.testyoutubeapi.bcReceiver.constValues.CHANNEL_ID
import com.example.testyoutubeapi.bcReceiver.constValues.NEXT_BUTTON
import com.example.testyoutubeapi.bcReceiver.constValues.PLAY_PAUSE_BUTTON


class NotificationBuilder(context: Context) {
    val mediSessionCompat = MediaSessionCompat(context, "tag")
    val backIntent = Intent(context, MyReceiver::class.java).apply {
        putExtra(BACK_BUTTON, BACK_BUTTON)

    }
    val playPauseIntent = Intent(context, MyReceiver::class.java).apply {
               putExtra(PLAY_PAUSE_BUTTON, PLAY_PAUSE_BUTTON)
    }
    val nextIntent = Intent(context, MyReceiver::class.java).apply {
        putExtra(NEXT_BUTTON, NEXT_BUTTON)
    }
    val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        PendingIntent.FLAG_IMMUTABLE
    } else {
        0
    }
    val playerState = MutableLiveData(false)
    val prevPendingIntent = PendingIntent.getBroadcast(context, 0, backIntent, flag)
    val pausePendingIntent = PendingIntent.getBroadcast(context, 1, playPauseIntent, flag)
    val nextPendingIntent = PendingIntent.getBroadcast(context, 2, nextIntent, flag)
    val playPauseButton =if(playerState.value == true){ R.drawable.pause_button} else { R.drawable.play_button}

    val provideNotificationBuilder =
        NotificationCompat.Builder(context, CHANNEL_ID)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSmallIcon(R.drawable.internal_music_item)
            .addAction(R.drawable.back_button, "Previous", prevPendingIntent) // #0
            .addAction(playPauseButton, "PlayPause", pausePendingIntent) // #1
            .addAction(R.drawable.next_button, "Next", nextPendingIntent) // #2
            .setContentTitle("Wonderful music")
            .setContentText("My Awesome Band")
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
                    .setMediaSession(mediSessionCompat.sessionToken)
            )



}