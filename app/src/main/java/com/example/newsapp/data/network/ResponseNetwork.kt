package com.example.newsapp.data.network

data class ResponseNetwork(
    val articles: List<ArticleNetworkEntity>,
    val status: String,
    val totalResults: Int
)