package com.example.testyoutubeapi.myPlayer

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

class MyPlayer(var context: Context) {

    val player = ExoPlayer.Builder(context).build()
    init{
        player.set
    }

    fun setVideByURL(url: String) {
        object : YouTubeExtractor(context) {
            override fun onExtractionComplete(
                ytFiles: SparseArray<YtFile>?,
                videoMeta: VideoMeta?
            ) {
                if (ytFiles != null) {
                    val itag = 137
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
                        true)
                    player.prepare()
                }
            }
        }.extract(url)
    }

    fun playVideobyUrl() {
        player.play()
    }

    fun nextVideoByUrl(url: String) {
        player.setMediaItem(
            MediaItem.fromUri(
                url
            )
        )
        player.play()
    }

    fun previousVideoByUrl(url: String) {
        player.setMediaItem(
            MediaItem.fromUri(
                url
            )
        )
        player.play()
    }

    fun pauseVideoByUrl() {
        player.pause()
    }

    fun getProgressOfVideUrl() {
        player.currentPosition / 1000
    }

    fun getDurationOfVideoUrl() {
        player.duration
    }

}