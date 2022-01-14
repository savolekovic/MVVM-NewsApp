package com.example.newsapp.presentation.main

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.domain.adapters.ArticleAdapter
import com.example.newsapp.domain.model.Article
import com.example.newsapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        articleAdapter = ArticleAdapter()
        subscribeObservers()
        viewModel.getArticlesEvent()
    }

    private fun subscribeObservers() {
        viewModel.dataState.observe(this, {
            when (it) {
                is DataState.Success<List<Article>> -> {
                    displayProgressBar(false)
                    updateArticlesAdapter(it.data)
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

    private fun updateArticlesAdapter(articles: List<Article>) {
        articleAdapter.setOnItemClickListener {
            Toast.makeText(this, "Article clicked", Toast.LENGTH_SHORT).show()
        }
        articles_recycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = articleAdapter
        }

        articleAdapter.differ.submitList(articles)
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Unknown error.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        loading.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}