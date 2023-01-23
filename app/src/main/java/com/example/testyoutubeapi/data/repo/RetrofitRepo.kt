package com.example.testyoutubeapi.data.repo

import com.example.testyoutubeapi.data.retrofit.RetrofitInstance
import com.example.testyoutubeapi.models.retrofit.getRequest.PlaylistItem
import com.example.testyoutubeapi.models.retrofit.playListNameResponse.PlayListNameResponse
import com.example.testyoutubeapi.models.retrofit.searchRequest.SearchRequest
import retrofit2.Response

class RetrofitRepo {
    suspend fun getColumnPlayList(): Response<PlaylistItem> {
        return RetrofitInstance.api.getColumnPlayList()
    }

    suspend fun getGridPlayList(): Response<PlaylistItem> {
        return RetrofitInstance.api.getGridPlayList()
    }

    suspend fun getPlayListName(playListId: String):Response<PlayListNameResponse>{
        return RetrofitInstance.api.getPlayListName(playListId)
    }

    suspend fun getSearchRequest(searchRequest:String):Response<SearchRequest>{
        return RetrofitInstance.api.getSearchRequest(searchRequest)
    }
}