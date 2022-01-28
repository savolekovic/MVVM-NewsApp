package com.example.newsapp.presentation.article_detail

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.domain.entities.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ArticleDetailViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
): ViewModel(){

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.saveArticle(article)
    }

}