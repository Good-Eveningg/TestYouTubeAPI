package com.example.testyoutubeapi.utils.audioFileFetcher

import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.os.Build
import android.provider.MediaStore
import android.provider.Settings
import androidx.annotation.RequiresApi
import com.example.testyoutubeapi.models.domain.LocalStorageAudioModel

class AudioFileFetcherImpl(
    private val context: Context
){
    @RequiresApi(Build.VERSION_CODES.R)
    fun getAllAudioFromDevice(): List<LocalStorageAudioModel> {
        val intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION)
        intent.addCategory("android.intent.category.DEFAULT")
        val tempAudioList: MutableList<LocalStorageAudioModel> = ArrayList()
        val uri =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                MediaStore.Audio.Media.getContentUri(
                    MediaStore.VOLUME_EXTERNAL_PRIMARY
                )
            } else {
                MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
            }
        val projection = arrayOf(
            MediaStore.Audio.AudioColumns.DATA,
            MediaStore.Audio.AudioColumns.TITLE,
            MediaStore.Audio.AudioColumns.ALBUM,
            MediaStore.Audio.ArtistColumns.ARTIST,

            )
        val c: Cursor? = context.contentResolver.query(
            uri,
            projection,
            null,
            null,
            null
        )
        if (c != null) {
            while (c.moveToNext()) {
                val path: String = c.getString(0)
                val name: String = c.getString(1)
                val album: String = c.getString(2)
                val artist: String = c.getString(3)
                val audioModel = LocalStorageAudioModel(aName = name, aPath =  path, aAlbum =  album, aArtist = artist, aAlbumPath = "")
                tempAudioList.add(audioModel)
            }
            c.close()
        }
        return tempAudioList
    }

}