package com.example.newsapp.presentation.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import com.example.newsapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        subscribeObservers()
        viewModel.getArticlesEvent()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, {
            when (it) {
                is DataState.Success<List<Article>> -> {
                    displayProgressBar(false)
                    appendBlogTitles(it.data)
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    displayProgressBar(true)
                }
            }
        })
    }

    private fun appendBlogTitles(articles: List<Article>) {
        val sb = StringBuilder()
        for (article in articles) {
            sb.append(article.title + "\n")
        }
        text.text = sb.toString()
    }

    private fun displayError(message: String?) {
        if (message != null) {
            text.text = message
        } else {
            text.text = "Unknown error."
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        loading.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}