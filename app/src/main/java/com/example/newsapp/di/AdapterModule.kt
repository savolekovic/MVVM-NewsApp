package com.example.newsapp.di

import com.example.newsapp.presentation.main.NewsAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Singleton
    @Provides
    @Named("breaking_news")
    fun provideBreakingNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }

    @Singleton
    @Provides
    @Named("search_news")
    fun provideSearchNewsAdapter(): NewsAdapter {
        return NewsAdapter()
    }

    @Singleton
    @Provides
    @Named("favorites_news")
    fun provideFavoritesAdapter(): NewsAdapter {
        return NewsAdapter()
    }

}