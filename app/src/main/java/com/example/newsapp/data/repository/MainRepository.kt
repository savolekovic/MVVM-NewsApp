package com.example.newsapp.data.repository

import com.example.newsapp.data.network.ArticleRetrofit
import com.example.newsapp.data.network.NetworkMapper
import com.example.newsapp.util.DataState
import kotlinx.coroutines.flow.flow
import java.lang.Exception

class MainRepository
constructor(
    private val articleRetrofit: ArticleRetrofit,
    private val networkMapper: NetworkMapper
){

    suspend fun getArticles() = flow{
        emit(DataState.Loading)
        try{
            val networkArticles = articleRetrofit.getArticles()
            val articles = networkMapper.mapFromEntityList(networkArticles)
            emit(DataState.Success(articles))
        }catch (e: Exception){
            emit(DataState.Error(e))
        }
    }
}