package com.example.newsapp.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleLocalEntity): Long

    @Query("SELECT * FROM articles")
    fun getArticles(): LiveData<List<ArticleLocalEntity>>

    @Delete
    suspend fun deleteArticle(articleLocalEntity: ArticleLocalEntity)

    @Query("DELETE FROM articles")
    suspend fun nukeArticles()

    @Query("SELECT COUNT(url) FROM articles WHERE url = :url")
    suspend fun isArticleSaved(url: String): Int

}
