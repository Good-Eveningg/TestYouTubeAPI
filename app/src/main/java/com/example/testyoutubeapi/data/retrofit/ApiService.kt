package com.example.testyoutubeapi.data.retrofit

import com.example.testyoutubeapi.models.retrofit.getRequest.PlaylistItem
import com.example.testyoutubeapi.models.retrofit.searchRequest.SearchRequest
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("playlistItems?part=snippet%2CcontentDetails&maxResults=25&playlistId=PL4fGSI1pDJn7524WZdmWAIRc6cQ3vUzZK&key=AIzaSyADBOEGYqJN7Snsm31qqhPh8TJInwSvq0g")
    suspend fun getFirstPlayList(): Response<PlaylistItem>

    @GET("playlistItems?part=snippet%2CcontentDetails&maxResults=50&playlistId=PL4fGSI1pDJn7524WZdmWAIRc6cQ3vUzZK&key=AIzaSyADBOEGYqJN7Snsm31qqhPh8TJInwSvq0g")
    suspend fun getSecondPlayList(): Response<PlaylistItem>

    @GET("search?part=snippet&type=videos&maxResults=10&key=AIzaSyADBOEGYqJN7Snsm31qqhPh8TJInwSvq0g")
    suspend fun getSearchRequest(
        @Query("q") query: String
    ): Response<SearchRequest>
}