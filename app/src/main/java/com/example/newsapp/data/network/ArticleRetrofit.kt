package com.example.newsapp.data.network

import com.example.newsapp.BuildConfig
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleRetrofit {

    @GET("v2/everything")
    suspend fun searchArticles(
        @Query("q")
        query: String,
        @Query("page")
        publishedAt: String = "1",
        @Query("apiKey")
        apiKey: String = BuildConfig.NEWS_API_KEY
    ): ArticlesResponse

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("country")
        query: String = "us",
        @Query("page")
        publishedAt: String = "1",
        @Query("apiKey")
        apiKey: String = BuildConfig.NEWS_API_KEY
    ): ArticlesResponse

}