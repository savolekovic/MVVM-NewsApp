package com.example.newsapp.data.network.model

data class NewsResponse(
    val articles: List<ArticleNetworkEntity>,
    val status: String,
    val totalResults: Int
)