package com.example.newsapp.data.repository

import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.LocalMapper
import com.example.newsapp.data.network.ArticleRetrofit
import com.example.newsapp.data.network.NetworkMapper
import com.example.newsapp.domain.entities.Article
import com.example.newsapp.util.DataState
import kotlinx.coroutines.flow.flow

class NewsRepository
constructor(
    private val articleDao: ArticleDao,
    private val articleRetrofit: ArticleRetrofit,
    private val localMapper: LocalMapper,
    private val networkMapper: NetworkMapper
) {

    suspend fun getBreakingNews() = flow {
        emit(DataState.Loading)
        try {
            val networkArticles = articleRetrofit.getBreakingNews()
            val articles = networkMapper.mapFromEntityList(networkArticles)
            emit(DataState.Success(articles))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun getAllArticles() = flow {
        emit(DataState.Loading)
        try {
            val networkArticles = articleRetrofit.getAllArticles()
            val articles = networkMapper.mapFromEntityList(networkArticles)
            emit(DataState.Success(articles))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    suspend fun saveArticle(article: Article) {
        articleDao.insertArticle(
            localMapper.mapToEntity(article)
        )
    }

    suspend fun getFavorites() = flow {
        emit(DataState.Loading)
        val localArticles = articleDao.getArticles()
        val articles = localMapper.mapFromEntityList(localArticles)
        emit(DataState.Success(articles))
    }

}