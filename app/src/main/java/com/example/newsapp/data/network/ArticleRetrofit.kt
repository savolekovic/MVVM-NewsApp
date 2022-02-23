package com.example.newsapp.data.network

import com.example.newsapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleRetrofit {

    @GET("v2/everything")
    suspend fun searchNews(
        @Query("q")
        query: String,
        @Query("page")
        page: Int ,
        @Query("apiKey")
        apiKey: String = BuildConfig.NEWS_API_KEY
    ): ResponseNetwork

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("page")
        page: Int,
        @Query("country")
        query: String = "us",
        @Query("apiKey")
        apiKey: String = BuildConfig.NEWS_API_KEY
    ): ResponseNetwork

}