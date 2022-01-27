package com.example.newsapp.di

import com.example.newsapp.data.network.ArticleRetrofit
import com.example.newsapp.data.network.NetworkMapper
import com.example.newsapp.data.repository.BreakingNewsRepository
import com.example.newsapp.data.repository.FavoritesRepository
import com.example.newsapp.data.repository.SearchRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    //Breaking News
    @Singleton
    @Provides
    fun provideBreakingNewsRepository(
        articleRetrofit: ArticleRetrofit,
        networkMapper: NetworkMapper
    ): BreakingNewsRepository{
        return BreakingNewsRepository(articleRetrofit, networkMapper)
    }

    //Favorites
    @Singleton
    @Provides
    fun provideFavoritesRepository(): FavoritesRepository{
        return FavoritesRepository()
    }

    //Search
    @Singleton
    @Provides
    fun provideSearchRepository(
        articleRetrofit: ArticleRetrofit,
        networkMapper: NetworkMapper
    ): SearchRepository{
        return SearchRepository(articleRetrofit, networkMapper)
    }
}