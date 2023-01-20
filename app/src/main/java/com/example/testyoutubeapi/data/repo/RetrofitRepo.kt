package com.example.testyoutubeapi.data.repo

import com.example.testyoutubeapi.data.retrofit.RetrofitInstance
import com.example.testyoutubeapi.models.retrofit.PlaylistItem
import retrofit2.Response

class RetrofitRepo {
    suspend fun getFirstPlayList(): Response<PlaylistItem> {
        return RetrofitInstance.api.getFirstPlayList()
    }

    suspend fun getSecondPlayList(): Response<PlaylistItem> {
        return RetrofitInstance.api.getFirstPlayList()
    }
}