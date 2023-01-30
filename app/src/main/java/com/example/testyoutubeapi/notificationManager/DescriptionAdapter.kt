package com.example.testyoutubeapi.notificationManager

import android.app.PendingIntent
import android.graphics.Bitmap
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.ui.PlayerNotificationManager

class DescriptionAdapter():  PlayerNotificationManager.MediaDescriptionAdapter {
    override fun getCurrentContentTitle(player: Player): CharSequence {
        TODO("Not yet implemented")

        // val window = player.currentMediaItemIndex
                // return getTitle(window)
    }

    override fun createCurrentContentIntent(player: Player): PendingIntent? {
        TODO("Not yet implemented")
    }

    override fun getCurrentContentText(player: Player): CharSequence? {
        TODO("Not yet implemented")
    }

    override fun getCurrentLargeIcon(
        player: Player,
        callback: PlayerNotificationManager.BitmapCallback
    ): Bitmap? {
        TODO("Not yet implemented")
    }
}