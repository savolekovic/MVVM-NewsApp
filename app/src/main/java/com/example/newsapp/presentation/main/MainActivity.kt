package com.example.newsapp.presentation.main

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.databinding.ActivityMainBinding
import com.example.newsapp.domain.adapters.ArticleAdapter
import com.example.newsapp.domain.model.Article
import com.example.newsapp.presentation.article_detail.ArticleDetailActivity
import com.example.newsapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        Log.d("test123", "onCreate")
        super.onCreate(savedInstanceState, persistentState)
    }

    override fun onStop() {
        Log.d("test123", "onStop")
        super.onStop()
    }

    override fun onPause() {
        Log.d("test123", "onPause")
        super.onPause()
    }

    override fun onDestroy() {
        Log.d("test123", "onDestroy")
        super.onDestroy()
    }

    override fun onStart() {
        Log.d("test123", "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d("test123", "onResume")
        super.onResume()
    }

    @Inject
    lateinit var articleAdapter: ArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
            val intent = Intent(this, ArticleDetailActivity::class.java)
            intent.putExtra("article", it)
            startActivity(intent)
        }
        binding.articlesRecycler.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = articleAdapter
        }

        articleAdapter.differ.submitList(articles)
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Log.d("AppDebug", message)
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Unknown error.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.loading.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}