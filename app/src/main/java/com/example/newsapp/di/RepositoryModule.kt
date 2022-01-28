package com.example.newsapp.di

import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.LocalMapper
import com.example.newsapp.data.network.ArticleRetrofit
import com.example.newsapp.data.network.NetworkMapper
import com.example.newsapp.data.repository.NewsRepository
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
    fun provideNewsRepository(
        articleDao: ArticleDao,
        articleRetrofit: ArticleRetrofit,
        localMapper: LocalMapper,
        networkMapper: NetworkMapper
    ): NewsRepository {
        return NewsRepository(articleDao, articleRetrofit, localMapper, networkMapper)
    }

}