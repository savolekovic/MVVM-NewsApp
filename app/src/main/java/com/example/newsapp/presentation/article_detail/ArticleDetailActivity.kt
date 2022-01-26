package com.example.newsapp.presentation.article_detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ActivityArticleDetailBinding
import com.example.newsapp.domain.model.Article
import com.example.newsapp.util.extensions.formatPublishedAt

class ArticleDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityArticleDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val article = intent.extras?.get("article") as Article

        updateUI(article)
    }

    private fun updateUI(article: Article) {
        binding.apply {
            articleTitle.text = article.title
            val author = "Author: ${article.author}"
            articleAuthor.text = author
            val publishedAt = "Published at: ${article.publishedAt.formatPublishedAt()}"
            articlePublishedAt.text = publishedAt
            Glide.with(root).load(article.image).into(articleImg)
            val source = "Source: ${article.source.name}"
            articleSource.text = source
            articleContent.text = article.content
        }
    }
}