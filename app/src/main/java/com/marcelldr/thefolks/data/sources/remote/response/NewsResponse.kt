package com.marcelldr.thefolks.data.sources.remote.response

data class NewsResponse(
    val source: String,
    val title: String,
    val description: String,
    val url: String,
    val urlToImage: String
)