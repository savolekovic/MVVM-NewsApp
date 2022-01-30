package com.example.newsapp.presentation.main.fragments.favorites

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.domain.entities.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
) : ViewModel() {

    fun getFavoriteArticles() = newsRepository.getFavoriteArticles()

    fun deleteArticle(article: Article) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.saveArticle(article)
    }

}
