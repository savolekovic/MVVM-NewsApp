package com.example.newsapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.newsapp.domain.entities.Source

@Entity(tableName = "articles")
data class ArticleLocalEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,

    val url: String,
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: Source,
    val title: String,
    val image: String
)
