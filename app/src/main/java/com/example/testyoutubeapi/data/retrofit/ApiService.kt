package com.example.testyoutubeapi.data.retrofit

import com.example.testyoutubeapi.models.retrofit.PlaylistItem
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {
    @GET("playlistItems?part=snippet%2CcontentDetails&maxResults=15&playlistId=PL4fGSI1pDJn7524WZdmWAIRc6cQ3vUzZK&key=AIzaSyADBOEGYqJN7Snsm31qqhPh8TJInwSvq0g")
    suspend fun getFirstPlayList(): Response<PlaylistItem>

    @GET("playlistItems?part=snippet%2CcontentDetails&maxResults=15&playlistId=PL4fGSI1pDJn7524WZdmWAIRc6cQ3vUzZK&key=AIzaSyADBOEGYqJN7Snsm31qqhPh8TJInwSvq0g")
    suspend fun getSecondPlayList(): Response<PlaylistItem>
}