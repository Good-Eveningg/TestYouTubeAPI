package com.example.testyoutubeapi.screens.youTubeScreen

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import at.huber.youtubeExtractor.YouTubeExtractor
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


    init {
        getPlayListForColumn()
        getPlayListForGrid()
    }


    fun putVideoInPlayer(videoId: String) {
        myPlayer.setVideByURL("https://www.youtube.com/watch?v=$videoId")
    }

    fun getPlayer(): ExoPlayer {
        return myPlayer.player
    }

    fun playVideo() {
        myPlayer.playVideobyUrl()
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