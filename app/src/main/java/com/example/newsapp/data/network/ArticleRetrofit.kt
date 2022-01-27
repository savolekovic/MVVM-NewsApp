package com.example.newsapp.data.network

import com.example.newsapp.data.network.model.NewsResponse
import com.example.newsapp.util.Constants.Companion.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query

interface ArticleRetrofit {

    @GET("v2/everything")
    suspend fun getAllArticles(
        @Query("sortBy")
        publishedAt: String = "publishedAt",
        @Query("q")
        query: String = "a",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

    @GET("v2/top-headlines")
    suspend fun getBreakingNews(
        @Query("sortBy")
        publishedAt: String = "publishedAt",
        @Query("country")
        query: String = "us",
        @Query("apiKey")
        apiKey: String = API_KEY
    ): NewsResponse

}