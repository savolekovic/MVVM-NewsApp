package com.example.newsapp.presentation.article_detail

import androidx.lifecycle.*
import com.example.newsapp.domain.entities.ArticleDomainEntity
import com.example.newsapp.domain.repository.NewsRepository
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

    private val _isArticleSaved: MutableLiveData<Int> = MutableLiveData()
    val isArticleSaved: LiveData<Int> = _isArticleSaved

    fun saveArticle(article: ArticleDomainEntity) = viewModelScope.launch {
        newsRepository.saveArticle(article)
    }

    fun isArticleSaved(url: String) = viewModelScope.launch {
        _isArticleSaved.value = newsRepository.isArticleSaved(url)
    }

    fun deleteArticle(article: ArticleDomainEntity) = viewModelScope.launch {
        newsRepository.deleteArticle(article)
    }

}