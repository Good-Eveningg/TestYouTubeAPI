package com.example.testyoutubeapi.models.retrofit.getRequest

data class PlaylistItem(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
)