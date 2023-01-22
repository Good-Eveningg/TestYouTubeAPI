package com.example.testyoutubeapi.data.repo

import com.example.testyoutubeapi.data.retrofit.RetrofitInstance
import com.example.testyoutubeapi.models.retrofit.getRequest.PlaylistItem
import com.example.testyoutubeapi.models.retrofit.searchRequest.SearchRequest
import retrofit2.Response

class RetrofitRepo {
    suspend fun getFirstPlayList(): Response<PlaylistItem> {
        return RetrofitInstance.api.getFirstPlayList()
    }

    suspend fun getSecondPlayList(): Response<PlaylistItem> {
        return RetrofitInstance.api.getSecondPlayList()
    }

    suspend fun getSearchRequest():Response<SearchRequest>{
        return RetrofitInstance.api.getSearchRequest("Ой у лузі")
    }
}