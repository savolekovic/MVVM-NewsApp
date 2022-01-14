package com.example.newsapp.di

import com.example.newsapp.domain.adapters.ArticleAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AdapterModule {

    @Singleton
    @Provides
    fun provideAdapter(): ArticleAdapter{
        return ArticleAdapter()
    }
}