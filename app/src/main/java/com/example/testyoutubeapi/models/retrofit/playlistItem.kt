package com.example.testyoutubeapi.models.retrofit

data class playlistItem(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo
)