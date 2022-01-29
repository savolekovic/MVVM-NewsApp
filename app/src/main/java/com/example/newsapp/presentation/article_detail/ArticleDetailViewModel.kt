package com.example.newsapp.presentation.article_detail

import androidx.lifecycle.*
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

    private val _isArticleSaved: MutableLiveData<Int> = MutableLiveData()
    val isArticleSaved: LiveData<Int> = _isArticleSaved

    fun saveArticle(article: Article) = viewModelScope.launch {
        newsRepository.saveArticle(article)
    }

    fun isArticleSaved(url: String) = viewModelScope.launch {
        _isArticleSaved.value = newsRepository.isArticleSaved(url)
    }

}