package com.example.newsapp.di

import com.example.newsapp.data.network.ArticleRetrofit
import com.example.newsapp.data.network.NetworkMapper
import com.example.newsapp.data.repository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideMainRepository(
        articleRetrofit: ArticleRetrofit,
        networkMapper: NetworkMapper
    ): MainRepository{
        return MainRepository(articleRetrofit, networkMapper)
    }
}