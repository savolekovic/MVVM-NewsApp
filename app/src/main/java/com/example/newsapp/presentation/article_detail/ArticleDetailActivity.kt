package com.example.newsapp.presentation.article_detail

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ActivityArticleDetailBinding
import com.example.newsapp.domain.entities.Article
import com.example.newsapp.util.extensions.formatPublishedAt
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ArticleDetailActivity : AppCompatActivity() {

    private val viewModel: ArticleDetailViewModel by viewModels()

    private lateinit var binding: ActivityArticleDetailBinding

    private lateinit var thisArticle: Article

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityArticleDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        thisArticle = intent.extras?.get("article") as Article

        updateUI()
        saveArticleOnClick()
    }

    private fun saveArticleOnClick() {
        binding.saveBtn.setOnClickListener {
            viewModel.saveArticle(thisArticle)
            Snackbar.make(binding.root, "Article saved successfully", Snackbar.LENGTH_SHORT).show()
        }
    }

    private fun updateUI() {
        binding.apply {
            articleTitle.text = thisArticle.title
            val author = "Author: ${thisArticle.author}"
            articleAuthor.text = author
            val publishedAt = "Published at: ${thisArticle.publishedAt.formatPublishedAt()}"
            articlePublishedAt.text = publishedAt
            Glide.with(root).load(thisArticle.image).into(articleImg)
            val source = "Source: ${thisArticle.source.name}"
            articleSource.text = source
            articleContent.text = thisArticle.content
        }
    }


}