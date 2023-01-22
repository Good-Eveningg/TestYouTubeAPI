package com.example.testyoutubeapi.models.retrofit.searchRequest

data class SearchRequest(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)