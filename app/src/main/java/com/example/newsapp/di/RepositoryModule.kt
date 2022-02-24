package com.example.newsapp.di

import com.example.newsapp.data.local.ArticleDao
import com.example.newsapp.data.local.LocalMapper
import com.example.newsapp.data.network.ArticleRetrofit
import com.example.newsapp.data.network.NetworkMapper
import com.example.newsapp.data.repository.NewsRepositoryImpl
import com.example.newsapp.domain.repository.NewsRepository
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
        return NewsRepositoryImpl(articleDao, articleRetrofit, localMapper, networkMapper)
    }

//    @Provides
//    @Singleton
//    fun provideNoteUseCases(repository: NoteRepository): NoteUseCases {
//        return NoteUseCases(
//            getNotes = GetNotes(repository),
//            deleteNote = DeleteNote(repository),
//            addNote = AddNote(repository),
//            getNote = GetNote(repository)
//        )
//    }

}