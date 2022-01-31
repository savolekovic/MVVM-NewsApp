package com.example.newsapp.data.repository

import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.LocalMapper
import com.example.newsapp.data.network.ArticleRetrofit
import com.example.newsapp.data.network.NetworkMapper
import com.example.newsapp.domain.entities.ArticleDomainEntity
import com.example.newsapp.util.DataState
import kotlinx.coroutines.flow.flow

class NewsRepository
constructor(
    private val articleDao: ArticleDao,
    private val articleRetrofit: ArticleRetrofit,
    private val localMapper: LocalMapper,
    private val networkMapper: NetworkMapper
) {

    suspend fun getBreakingNews(breakingNewsPage: Int) = flow {
        emit(DataState.Loading)
        try {
            val networkResponse = articleRetrofit.getBreakingNews(breakingNewsPage)
            val domainResponse = networkMapper.mapFromResponse(networkResponse)
            emit(DataState.Success(domainResponse))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun searchNews(query: String, searchNewsPage: Int) = flow {
        emit(DataState.Loading)
        try {
            val networkResponse = articleRetrofit.searchNews(query, searchNewsPage)
            val domainResponse = networkMapper.mapFromResponse(networkResponse)
            emit(DataState.Success(domainResponse))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    fun getFavoriteArticles() = articleDao.getArticles()

    suspend fun saveArticle(article: ArticleDomainEntity) {
        articleDao.insertArticle(
            localMapper.mapToEntity(article)
        )
    }

    suspend fun deleteArticle(article: ArticleDomainEntity) {
        articleDao.deleteArticle(
            localMapper.mapToEntity(article)
        )
    }

    suspend fun isArticleSaved(url: String): Int {
        return articleDao.isArticleSaved(url)
    }

}