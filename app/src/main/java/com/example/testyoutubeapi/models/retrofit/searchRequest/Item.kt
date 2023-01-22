package com.example.testyoutubeapi.models.retrofit.searchRequest

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)