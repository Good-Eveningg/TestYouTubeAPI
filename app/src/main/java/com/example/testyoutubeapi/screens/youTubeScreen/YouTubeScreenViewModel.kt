package com.example.testyoutubeapi.screens.youTubeScreen

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyoutubeapi.data.repo.RetrofitRepo
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import com.example.testyoutubeapi.myPlayer.MyPlayer
import com.example.testyoutubeapi.notificationManager.NotificationBuilder
import com.example.testyoutubeapi.notificationManager.NotificationManager
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class YouTubeScreenViewModel(
    private val youTubeListRepo: RetrofitRepo,
    private val myPlayer: MyPlayer,
    private val notificationManager: NotificationManager,
    private val notificationBuilder: NotificationBuilder
) : ViewModel() {

    private val _playListForRow = MutableLiveData<List<Item>>()
    val playListForRow: LiveData<List<Item>> = _playListForRow

    private val _playListForGrid = MutableLiveData<List<Item>>()
    val playListForGrid: LiveData<List<Item>> = _playListForGrid

    private val _searchRequestResult =
        MutableLiveData<List<com.example.testyoutubeapi.models.retrofit.searchRequest.Item>>()
    val searchRequestResult: LiveData<List<com.example.testyoutubeapi.models.retrofit.searchRequest.Item>> =
        _searchRequestResult

    private val _namePlayListForRow = MutableLiveData<String>()
    val namePlayListForRow: LiveData<String> = _namePlayListForRow

    private val _namePlayListForGrid = MutableLiveData<String>()
    val namePlayListForGrid: LiveData<String> = _namePlayListForGrid

    private val _videoProgress = MutableLiveData(0L)
    val videoProgress: LiveData<Long> = _videoProgress


    private val _videoDuration = MutableLiveData(1L)
    val videoDuration: LiveData<Long> = _videoDuration

    private val _searchWidgetState = MutableLiveData(SearchWidgetState.CLOSED)
    val searchWidgetState: LiveData<SearchWidgetState> = _searchWidgetState

    private val _searchTextState = MutableLiveData("")
    val searchTextState: LiveData<String> = _searchTextState

    private val _videImportedInYouTube = MutableLiveData(false)
    val videoImportedInYoutube: LiveData<Boolean> = _videImportedInYouTube

    private val _isPlayerPlaying = MutableLiveData<Boolean>()
    val isPlayerPlaying: LiveData<Boolean> = _isPlayerPlaying

    private val _currentItem = MutableLiveData<Item>()
    val currentItem: LiveData<Item> = _currentItem

    private var _playListType = -1
    private val _onPlayerClicked = MutableLiveData(false)
    val onPlayerClicked: LiveData<Boolean> = _onPlayerClicked



    init {
        getPlayListForColumn()
        getPlayListForGrid()
        updateProgress()
    }

    fun screenChangedToInternal(){
        _videImportedInYouTube.postValue(false)
    }

    @SuppressLint("MissingPermission")
    fun createUpdateNotification(videoTitle: String, channelTitle: String) {
        viewModelScope.launch {
            notificationBuilder.playerState.postValue(isPlayerPlaying.value)
            notificationManager.createNotificationChannel()
            notificationManager.notificationManager.notify(
                1,
                notificationBuilder.provideNotificationBuilder.setContentTitle(videoTitle)
                    .setContentText(channelTitle).build()
            )
        }
    }


    fun setProgress(progress: Float) {
        myPlayer.setProgress(progress)
    }

    fun setPlayerState(state: Boolean) {
        _onPlayerClicked.postValue(state)
    }

    fun clearSearchList() {
        _searchRequestResult.postValue(emptyList())
    }

    private fun updateProgress() {
        viewModelScope.launch {
            while (true) {
                delay(100)
                if (_videImportedInYouTube.value == true) {
                    _videoProgress.postValue(myPlayer.getProgress())
                    _videoDuration.postValue(myPlayer.getDuration())
                }
            }
        }
    }

    fun setVideoId(itemId: Int, playlistType: Int) {
        when (playlistType) {
            0 -> {
                val item = _playListForRow.value?.get(itemId)!!
                _playListType = playlistType
                putVideoInPlayer(item.contentDetails.videoId)
                createUpdateNotification(item.snippet.title, item.snippet.videoOwnerChannelTitle)
                _currentItem.postValue(item)
            }
            1 -> {
                val item = _playListForGrid.value?.get(itemId)!!
                _playListType = playlistType
                putVideoInPlayer(item.contentDetails.videoId)
                createUpdateNotification(item.snippet.title, item.snippet.videoOwnerChannelTitle)
                _currentItem.postValue(item)
            }
            2 -> {
                val item = _searchRequestResult.value?.get(itemId)
                if (item != null) {
                    putVideoInPlayer(item.id.videoId)
                    createUpdateNotification(item.snippet.title, item.snippet.channelTitle)
                }
            }
        }

    }

    fun putVideoInPlayer(videoId: String) {
        myPlayer.setVideByURL("https://www.youtube.com/watch?v=$videoId")
        _videImportedInYouTube.postValue(true)

    }

    fun getPlayer(): ExoPlayer {
        return myPlayer.player
    }


    fun nextVideo() {
        when (_playListType) {
            0 -> {
                val currentItemPosition = _currentItem.value?.let {
                    _playListForRow.value?.indexOf(
                        it
                    )
                }
                if (currentItemPosition != (_playListForRow.value?.size?.minus(1))) {
                    val nextVideoIdString = currentItemPosition?.plus(1)
                        ?.let { _playListForRow.value?.get(it)?.contentDetails?.videoId }
                    if (nextVideoIdString != null) {
                        putVideoInPlayer(nextVideoIdString)

                        val item = _playListForRow.value?.get(currentItemPosition + 1)
                        if (item != null) {
                            _currentItem.postValue(item)
                            createUpdateNotification(
                                item.snippet.title,
                                item.snippet.videoOwnerChannelTitle
                            )
                        }
                        _isPlayerPlaying.postValue(myPlayer.player.isPlaying)
                    }
                }
            }
            1 -> {
                val currentItemPosition = _currentItem.value?.let {
                    _playListForGrid.value?.indexOf(
                        it
                    )
                }
                if (currentItemPosition != (_playListForGrid.value?.size?.minus(1))) {
                    val nextVideoIdString = currentItemPosition?.plus(1)
                        ?.let { _playListForGrid.value?.get(it)?.contentDetails?.videoId }
                    if (nextVideoIdString != null) {
                        putVideoInPlayer(nextVideoIdString)
                        val item = _playListForGrid.value?.get(currentItemPosition + 1)
                        if (item != null) {
                            _currentItem.postValue(item)
                            createUpdateNotification(
                                item.snippet.title,
                                item.snippet.videoOwnerChannelTitle
                            )
                        }
                        _isPlayerPlaying.postValue(myPlayer.player.isPlaying)
                    }
                }
            }
        }
    }

    fun backVideo() {
        when (_playListType) {
            0 -> {
                val currentItemPosition = _currentItem.value?.let {
                    _playListForRow.value?.indexOf(
                        it
                    )
                }
                if (currentItemPosition != 0) {
                    val nextVideoIdString = currentItemPosition?.minus(1)
                        ?.let { _playListForRow.value?.get(it)?.contentDetails?.videoId }
                    if (nextVideoIdString != null) {
                        putVideoInPlayer(nextVideoIdString)
                        val item = _playListForRow.value?.get(currentItemPosition - 1)
                        if (item != null) {
                            _currentItem.postValue(item)
                            createUpdateNotification(
                                item.snippet.title,
                                item.snippet.videoOwnerChannelTitle
                            )
                        }
                        _isPlayerPlaying.postValue(myPlayer.player.isPlaying)
                    }
                }
            }
            1 -> {
                val currentItemPosition = _currentItem.value?.let {
                    _playListForGrid.value?.indexOf(
                        it
                    )
                }
                if (currentItemPosition != 0) {
                    val nextVideoIdString = currentItemPosition?.minus(1)
                        ?.let { _playListForGrid.value?.get(it)?.contentDetails?.videoId }
                    if (nextVideoIdString != null) {
                        putVideoInPlayer(nextVideoIdString)
                        val item = _playListForGrid.value?.get(currentItemPosition - 1)
                        if (item != null) {
                            _currentItem.postValue(item)
                            createUpdateNotification(
                                item.snippet.title,
                                item.snippet.videoOwnerChannelTitle
                            )
                        }
                        _isPlayerPlaying.postValue(myPlayer.player.isPlaying)
                    }
                }
            }
        }
    }

    fun playPauseVideo() {
        try {
            if (!myPlayer.player.isPlaying) {
                myPlayer.playVideoAudio()
                _isPlayerPlaying.postValue(myPlayer.player.isPlaying)

            } else {
                myPlayer.pauseVideoAudio()
                _isPlayerPlaying.postValue(myPlayer.player.isPlaying)

            }
        } catch (_: Exception) {
        }
    }

    fun updateSearchWidgetState(newValue: SearchWidgetState) {
        _searchWidgetState.value = newValue
    }

    fun updateSearchTextState(newValue: String) {
        _searchTextState.value = newValue
    }


    fun searchRequest(searchRequest: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _searchRequestResult.postValue(
                youTubeListRepo.getSearchRequest(searchRequest).body()?.items
            )
        }
    }

    fun getPlayListForColumn() {
        viewModelScope.launch(Dispatchers.IO) {
            val columnPlayListResponse = youTubeListRepo.getColumnPlayList().body()?.items
            _playListForRow.postValue(
                columnPlayListResponse
            )
            _namePlayListForRow.postValue(
                columnPlayListResponse?.get(0)?.snippet?.let {
                    youTubeListRepo.getPlayListName(it.playlistId)
                        .body()?.items?.get(0)?.snippet?.title
                }
                    ?: "Cannot resolve")

        }
    }

    fun getPlayListForGrid() {
        viewModelScope.launch(Dispatchers.IO) {
            val gridPlayListResponse = youTubeListRepo.getGridPlayList().body()?.items
            _playListForGrid.postValue(gridPlayListResponse)
            _namePlayListForGrid.postValue(
                gridPlayListResponse?.get(0)?.snippet?.let {
                    youTubeListRepo.getPlayListName(it.playlistId)
                        .body()?.items?.get(0)?.snippet?.title
                }
                    ?: "Cannot resolve")
        }
    }

}