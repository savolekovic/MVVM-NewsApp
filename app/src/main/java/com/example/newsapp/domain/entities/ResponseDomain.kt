package com.example.newsapp.domain.entities

data class ResponseDomain(
    val articles: MutableList<ArticleDomainEntity>,
    val status: String,
    val totalResults: Int
)