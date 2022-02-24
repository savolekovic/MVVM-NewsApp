package com.example.newsapp.domain.repository

import androidx.lifecycle.LiveData
import com.example.newsapp.data.local.ArticleLocalEntity
import com.example.newsapp.domain.entities.ArticleDomainEntity
import com.example.newsapp.domain.entities.ResponseDomain
import com.example.newsapp.util.DataState
import kotlinx.coroutines.flow.Flow

interface NewsRepository{

    suspend fun getBreakingNews(breakingNewsPage: Int): Flow<DataState<ResponseDomain>>

    suspend fun searchNews(query: String, searchNewsPage: Int): Flow<DataState<ResponseDomain>>

    fun getFavoriteArticles(): LiveData<List<ArticleLocalEntity>>

    suspend fun saveArticle(article: ArticleDomainEntity)

    suspend fun deleteArticle(article: ArticleDomainEntity)

    suspend fun isArticleSaved(url: String): Int

}