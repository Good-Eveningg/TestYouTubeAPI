package com.example.testyoutubeapi.screens.youTubeScreen

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testyoutubeapi.data.repo.RetrofitRepo
import com.example.testyoutubeapi.models.retrofit.getRequest.Item
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class YouTubeScreenViewModel : ViewModel(){
    var repo = RetrofitRepo()
    val firstPlayList: MutableLiveData<List<Item>> = MutableLiveData()
    val searchRequestResult: MutableLiveData<List<com.example.testyoutubeapi.models.retrofit.searchRequest.Item>> = MutableLiveData()


    init {
        getFirstPlayList()
    }
    fun getFirstPlayList(){
        viewModelScope.launch(Dispatchers.IO){
            firstPlayList.postValue(
                repo.getFirstPlayList().body()?.items
            )

        }
    }




}