package com.example.newsapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertArticle(article: ArticleLocalEntity): Long

    @Query("SELECT * FROM articles")
    suspend fun getArticles(): List<ArticleLocalEntity>
}
