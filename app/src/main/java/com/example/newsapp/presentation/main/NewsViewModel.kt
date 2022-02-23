package com.example.newsapp.presentation.main

import android.util.Log
import androidx.lifecycle.*
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.domain.entities.ArticleDomainEntity
import com.example.newsapp.domain.entities.ResponseDomain
import com.example.newsapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _breakingNews: MutableLiveData<DataState<ResponseDomain>> = MutableLiveData()
    val breakingNews: LiveData<DataState<ResponseDomain>> = _breakingNews
    var breakingNewsPage = 1
    private var breakingNewsResponse: ResponseDomain? = null

    private val _searchNews: MutableLiveData<DataState<ResponseDomain>> = MutableLiveData()
    val searchNews: LiveData<DataState<ResponseDomain>> = _searchNews
    var searchNewsPage = 1
    private var searchNewsResponse: ResponseDomain? = null

    init {
        getBreakingNews()
    }

    fun getBreakingNews() {
        viewModelScope.launch {
            newsRepository.getBreakingNews(breakingNewsPage)
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Success<ResponseDomain> -> {
                            breakingNewsPage++
                            if (breakingNewsResponse == null) {
                                breakingNewsResponse = dataState.data
                            } else {
                                val newArticles = dataState.data.articles
                                breakingNewsResponse!!.articles.addAll(newArticles)
                            }
                            _breakingNews.value =
                                DataState.Success(breakingNewsResponse ?: dataState.data)
                        }
                        is DataState.Error -> {
                            _breakingNews.value = dataState
                        }
                        is DataState.Loading -> {
                            _breakingNews.value = dataState
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun searchNews(query: String, shouldResetResponse: Boolean) {
        if (shouldResetResponse) {
            searchNewsResponse = null
            searchNewsPage = 1
        }
        viewModelScope.launch {
            newsRepository.searchNews(query, searchNewsPage)
                .onEach { dataState ->
                    when (dataState) {
                        is DataState.Success<ResponseDomain> -> {
                            searchNewsPage++
                            if (searchNewsResponse == null) {
                                searchNewsResponse = dataState.data
                            } else {
                                val newArticles = dataState.data.articles
                                searchNewsResponse!!.articles.addAll(newArticles)
                            }
                            _searchNews.value =
                                DataState.Success(searchNewsResponse ?: dataState.data)
                        }
                        is DataState.Error -> {
                            _searchNews.value = dataState
                        }
                        is DataState.Loading -> {
                            _searchNews.value = dataState
                        }
                    }
                }
                .launchIn(viewModelScope)
        }
    }

    fun getFavoriteArticles() = newsRepository.getFavoriteArticles()

    fun deleteArticle(article: ArticleDomainEntity) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun saveArticle(article: ArticleDomainEntity) = viewModelScope.launch {
        newsRepository.saveArticle(article)
    }
}