package com.example.testyoutubeapi.screens.youTubeScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyoutubeapi.data.repo.RetrofitRepo
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import com.example.testyoutubeapi.myPlayer.MyPlayer
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YouTubeScreenViewModel(
    private val youTubeListRepo: RetrofitRepo,
    private val myPlayer: MyPlayer
) : ViewModel() {

    val playListForRow: MutableLiveData<List<Item>> = MutableLiveData()
    val playListForGrid: MutableLiveData<List<Item>> = MutableLiveData()
    val searchRequestResult: MutableLiveData<List<com.example.testyoutubeapi.models.retrofit.searchRequest.Item>> =
        MutableLiveData()
    val namePlayListForRow: MutableLiveData<String> = MutableLiveData()
    val namePlayListForGrid: MutableLiveData<String> = MutableLiveData()
    val videoDurationProgress: MutableLiveData<Float> = MutableLiveData()
    private val _searchWidgetState: MutableState<SearchWidgetState> =
        mutableStateOf(value = SearchWidgetState.CLOSED)
    val searchWidgetState: State<SearchWidgetState> = _searchWidgetState
    private val _searchTextState: MutableState<String> =
        mutableStateOf(value = "")
    val searchTextState: State<String> = _searchTextState
    val videImported: MutableLiveData<Boolean> = MutableLiveData()
    val isPlayerPlaying: MutableLiveData<Boolean> = MutableLiveData()
    val currentItem: MutableLiveData<Item> = MutableLiveData()
    private var _playListType = -1
    val onPlayerClicked: MutableLiveData<Boolean> = MutableLiveData()


    init {
        getPlayListForColumn()
        getPlayListForGrid()
        videImported.postValue(false)
        onPlayerClicked.postValue(false)
    }

    private fun calculateProgress() {
        while (isPlayerPlaying.value == true) {
            viewModelScope.launch(Dispatchers.IO) {
                val progress =
                    ((myPlayer.getProgressOfVideUrl() / myPlayer.getDurationOfVideoUrl()).toFloat())
                videoDurationProgress.postValue(progress)
            }
        }
    }

    fun setVideoId(itemId: Int, playlistType: Int) {


        when (playlistType) {
            0 -> {
                val item = playListForRow.value?.get(itemId)!!
                _playListType = playlistType
                putVideoInPlayer(item.contentDetails.videoId)
                currentItem.postValue(item)
            }
            1 -> {
                val item= playListForGrid.value?.get(itemId)!!
                _playListType = playlistType
                putVideoInPlayer(item.contentDetails.videoId)
                currentItem.postValue(item)
            }
            2 -> {
                val _item = searchRequestResult.value?.get(itemId)
                if (_item != null) {
                    putVideoInPlayer(_item.id.videoId)

                }
            }
        }

    }


    fun putVideoInPlayer(videoId: String) {
        myPlayer.setVideByURL("https://www.youtube.com/watch?v=$videoId")
        videImported.postValue(true)
    }

    fun getPlayer(): ExoPlayer {
        return myPlayer.player
    }


    fun nextVideo() {
        when (_playListType) {
            0 -> {
                val currentItemPosition = currentItem.value?.let {
                    playListForRow.value?.indexOf(
                        it
                    )
                }
                if (currentItemPosition != (playListForRow.value?.size?.minus(1))) {
                    val nextVideoIdString = currentItemPosition?.plus(1)
                        ?.let { playListForRow.value?.get(it)?.contentDetails?.videoId }
                    if (nextVideoIdString != null) {
                        putVideoInPlayer(nextVideoIdString)
                        currentItem.postValue(playListForRow.value?.get(currentItemPosition + 1))
                        isPlayerPlaying.postValue(myPlayer.player.isPlaying)
                    }
                }
            }
            1 -> {
                val currentItemPosition = currentItem.value?.let {
                    playListForGrid.value?.indexOf(
                        it
                    )
                }
                if (currentItemPosition != (playListForGrid.value?.size?.minus(1))) {
                    val nextVideoIdString = currentItemPosition?.plus(1)
                        ?.let { playListForGrid.value?.get(it)?.contentDetails?.videoId }
                    if (nextVideoIdString != null) {
                        putVideoInPlayer(nextVideoIdString)
                        currentItem.postValue(playListForGrid.value?.get(currentItemPosition + 1))
                        isPlayerPlaying.postValue(myPlayer.player.isPlaying)
                    }
                }
            }
        }
    }

    fun previousVideo() {
        when (_playListType) {
            0 -> {
                val currentItemPosition = currentItem.value?.let {
                    playListForRow.value?.indexOf(
                        it
                    )
                }
                if (currentItemPosition != 0) {
                    val nextVideoIdString = currentItemPosition?.minus(1)
                        ?.let { playListForRow.value?.get(it)?.contentDetails?.videoId }
                    if (nextVideoIdString != null) {
                        putVideoInPlayer(nextVideoIdString)
                        currentItem.postValue(playListForRow.value?.get(currentItemPosition - 1))
                        isPlayerPlaying.postValue(myPlayer.player.isPlaying)
                    }
                }
            }
            1 -> {
                val currentItemPosition = currentItem.value?.let {
                    playListForGrid.value?.indexOf(
                        it
                    )
                }
                if (currentItemPosition != 0) {
                    val nextVideoIdString = currentItemPosition?.minus(1)
                        ?.let { playListForGrid.value?.get(it)?.contentDetails?.videoId }
                    if (nextVideoIdString != null) {
                        putVideoInPlayer(nextVideoIdString)
                        currentItem.postValue(playListForGrid.value?.get(currentItemPosition - 1))
                        isPlayerPlaying.postValue(myPlayer.player.isPlaying)
                    }
                }
            }
        }
    }

    fun playPauseVideo() {
        try {
            if (!myPlayer.player.isPlaying) {
                myPlayer.playVideoAudio()
                isPlayerPlaying.postValue(myPlayer.player.isPlaying)
                calculateProgress()
            } else {
                myPlayer.pauseVideoAudio()
                isPlayerPlaying.postValue(myPlayer.player.isPlaying)
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
            searchRequestResult.postValue(
                youTubeListRepo.getSearchRequest(searchRequest).body()?.items
            )
        }
    }

    fun getPlayListForColumn() {
        viewModelScope.launch(Dispatchers.IO) {
            val columnPlayListResponse = youTubeListRepo.getColumnPlayList().body()?.items
            playListForRow.postValue(
                columnPlayListResponse
            )
            namePlayListForRow.postValue(
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
            playListForGrid.postValue(gridPlayListResponse)
            namePlayListForGrid.postValue(
                gridPlayListResponse?.get(0)?.snippet?.let {
                    youTubeListRepo.getPlayListName(it.playlistId)
                        .body()?.items?.get(0)?.snippet?.title
                }
                    ?: "Cannot resolve")
        }
    }

}