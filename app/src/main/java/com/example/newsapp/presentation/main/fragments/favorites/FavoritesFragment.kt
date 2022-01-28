package com.example.newsapp.presentation.main.fragments.favorites

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.newsapp.R
import com.example.newsapp.databinding.FragmentFavoritesBinding
import com.example.newsapp.domain.adapters.ArticleAdapter
import com.example.newsapp.domain.entities.Article
import com.example.newsapp.presentation.article_detail.ArticleDetailActivity
import com.example.newsapp.util.DataState
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    private lateinit var binding: FragmentFavoritesBinding

    private val viewModel: FavoritesViewModel by viewModels()

    @Inject
    @Named("favorites_news")
    lateinit var articleAdapter: ArticleAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)

        subscribeObservers()
        viewModel.getArticlesEvent()

        return binding.root
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
            val intent = Intent(requireActivity(), ArticleDetailActivity::class.java)
            intent.putExtra("article", it)
            startActivity(intent)
        }
        binding.articlesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }

        articleAdapter.differ.submitList(articles)
    }

    private fun displayError(message: String?) {
        if (message != null) {
            Log.d("AppDebug", message)
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Unknown error.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.loading.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }
}