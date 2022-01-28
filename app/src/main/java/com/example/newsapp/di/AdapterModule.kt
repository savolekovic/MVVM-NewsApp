package com.example.newsapp.di

import com.example.newsapp.domain.adapters.ArticleAdapter
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
    fun provideBreakingNewsAdapter(): ArticleAdapter{
        return ArticleAdapter()
    }

    @Singleton
    @Provides
    @Named("search_news")
    fun provideSearchNewsAdapter(): ArticleAdapter{
        return ArticleAdapter()
    }

    @Singleton
    @Provides
    @Named("favorites_news")
    fun provideFavoritesAdapter(): ArticleAdapter{
        return ArticleAdapter()
    }

}