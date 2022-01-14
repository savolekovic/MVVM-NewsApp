package com.example.newsapp.domain.model

import com.example.newsapp.data.network.model.Source

data class Article (
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val url: String,
    val image: String
)
