package com.example.newsapp.presentation.main.fragments.favorites

import androidx.lifecycle.*
import com.example.newsapp.data.repository.NewsRepository
import com.example.newsapp.domain.entities.Article
import com.example.newsapp.util.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel
@Inject
constructor(
    private val savedStateHandle: SavedStateHandle,
    private val newsRepository: NewsRepository
) : ViewModel() {

    private val _dataState: MutableLiveData<DataState<List<Article>>> = MutableLiveData()
    val dataState: LiveData<DataState<List<Article>>> = _dataState

    fun getArticlesEvent(){
        viewModelScope.launch {
            newsRepository.getFavorites()
                .onEach { dataState ->
                    _dataState.value = dataState
                }
                .launchIn(viewModelScope)
        }
    }
}