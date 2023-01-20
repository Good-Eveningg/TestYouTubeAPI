package com.example.testyoutubeapi.models.retrofit

data class PlaylistItem(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
)