package com.example.testyoutubeapi.screens.youTubeScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyoutubeapi.data.repo.RetrofitRepo
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YouTubeScreenViewModel : ViewModel() {
    var youTubeListRepo = RetrofitRepo()
    val playListForColumn: MutableLiveData<List<Item>> = MutableLiveData()
    val playListForGrid: MutableLiveData<List<Item>> = MutableLiveData()
    val searchRequestResult: MutableLiveData<List<com.example.testyoutubeapi.models.retrofit.searchRequest.Item>> =
        MutableLiveData()
    val namePlayListForColumn: MutableLiveData<String> = MutableLiveData()
    val namePlayListForGrid: MutableLiveData<String> = MutableLiveData()
    var playListName = ""


    init {
        getPlayListForColumn()
        getPlayListForGrid()
    }

    fun getPlayListForColumn() {
        viewModelScope.launch(Dispatchers.IO) {
            playListForColumn.postValue(
                youTubeListRepo.getFirstPlayList().body()?.items
            )

            playListName =
                playListForColumn.value?.get(0)?.snippet?.playlistId?.let {
                    youTubeListRepo.getPlayListName(
                        it
                    ).body()?.items?.get(0)?.snippet?.title.toString()
                }.toString()
        }
    }

    fun getPlayListForGrid() {
        viewModelScope.launch(Dispatchers.IO) {
            playListForGrid.postValue(youTubeListRepo.getSecondPlayList().body()?.items)

        }
    }

}