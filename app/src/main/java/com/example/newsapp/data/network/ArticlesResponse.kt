package com.example.newsapp.data.network

data class ArticlesResponse(
    val articles: List<ArticleNetworkEntity>,
    val status: String,
    val totalResults: Int
)