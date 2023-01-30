package com.example.testyoutubeapi.myPlayer

import android.annotation.SuppressLint
import android.content.Context
import android.util.SparseArray
import at.huber.youtubeExtractor.VideoMeta
import at.huber.youtubeExtractor.YouTubeExtractor
import at.huber.youtubeExtractor.YtFile
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.MergingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSource

class MyPlayer(val context: Context) {

    val player = ExoPlayer.Builder(context).build()


    @SuppressLint("StaticFieldLeak")
    fun setVideByURL(url: String) {
        object : YouTubeExtractor(context) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {
                    val itag = 18
                    val audioTag = 140
                    val videoUrl = ytFiles[itag].url
                    val audioUrl = ytFiles[audioTag].url
                    val audioSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(audioUrl))
                    val videoSource: MediaSource = ProgressiveMediaSource
                        .Factory(DefaultHttpDataSource.Factory())
                        .createMediaSource(MediaItem.fromUri(videoUrl))
                    player.setMediaSource(
                        MergingMediaSource
                            (true, videoSource, audioSource),
                        true
                    )
                    player.prepare()
                }
            }
        }.extract(url)
    }

    fun setAudio(path: String) {
            val mediaItem = MediaItem.fromUri(path)
            player.setMediaItem(mediaItem)
            player.prepare()
    }

    fun playVideoAudio() {
        player.play()
    }

    fun pauseVideoAudio() {
        player.pause()
    }


    fun getProgress(): Long {
        return player.currentPosition
    }

    fun setProgress(progress: Float){
        player.seekTo(progress.toLong())
    }

    fun getDuration(): Long {
        return player.duration
    }


}