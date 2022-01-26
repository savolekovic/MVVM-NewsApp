package com.example.newsapp.data.network.model

import com.example.newsapp.domain.model.Source

data class ArticleNetworkEntity(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val urlToImage: String
)