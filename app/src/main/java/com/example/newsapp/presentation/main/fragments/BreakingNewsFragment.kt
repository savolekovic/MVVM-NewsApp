package com.example.newsapp.presentation.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.databinding.FragmentBreakingNewsBinding
import com.example.newsapp.domain.entities.ResponseDomain
import com.example.newsapp.presentation.article_detail.ArticleDetailActivity
import com.example.newsapp.presentation.main.NewsActivity
import com.example.newsapp.presentation.main.NewsAdapter
import com.example.newsapp.presentation.main.NewsViewModel
import com.example.newsapp.util.Constants.Companion.QUERY_PAGE_SIZE
import com.example.newsapp.util.DataState
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class BreakingNewsFragment : Fragment() {

    private var _binding: FragmentBreakingNewsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    lateinit var viewModel: NewsViewModel

    @Inject
    @Named("breaking_news")
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBreakingNewsBinding.inflate(layoutInflater)

        viewModel = (activity as NewsActivity).viewModel

        setupRecyclerView()
        subscribeObservers()

        return binding.root
    }

    private fun subscribeObservers() {
        viewModel.breakingNews.observe(viewLifecycleOwner) {
            when (it) {
                is DataState.Success<ResponseDomain> -> {
                    displayProgressBar(false)
                    newsAdapter.differ.submitList(it.data.articles.toList())

                    val totalPages = it.data.totalResults / QUERY_PAGE_SIZE + 2
                    isLastPage = viewModel.breakingNewsPage == totalPages
                    if (isLastPage) {
                        val scale = resources.displayMetrics.density
                        val dp4 = (4 * scale + 0.5f).toInt()
                        binding.articlesRecycler.setPadding(dp4 * 2, dp4, dp4 * 2, dp4)
                    }
                }
                is DataState.Error -> {
                    displayProgressBar(false)
                    displayError(it.exception.message)
                }
                is DataState.Loading -> {
                    if (viewModel.breakingNewsPage != 1) {
                        binding.mainProgressBar.visibility = View.GONE
                        displayProgressBar(true)
                    }
                }
            }
        }
    }

    private fun setupRecyclerView() {
        newsAdapter.setOnItemClickListener {
            val intent = Intent(requireActivity(), ArticleDetailActivity::class.java)
            intent.putExtra("article", it)
            startActivity(intent)
        }
        binding.articlesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
            addOnScrollListener(this@BreakingNewsFragment.scrollListener)
        }
    }

    private fun displayError(message: String?) {
        if (message != null) {
            if (message.contains("HTTP 426")) {
                isLastPage = true
                Snackbar.make(binding.root, "Only 100 results available!", Snackbar.LENGTH_SHORT)
                    .show()
            } else Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        } else Snackbar.make(binding.root, "Unknown error.", Snackbar.LENGTH_SHORT).show()
    }

    private fun displayProgressBar(isDisplayed: Boolean) {
        binding.mainProgressBar.visibility = View.GONE
        isLoading = isDisplayed
        binding.paginationProgressBar.visibility = if (isDisplayed) View.VISIBLE else View.GONE
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    private val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if (newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }

        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= QUERY_PAGE_SIZE
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling

            if (shouldPaginate) {
                viewModel.getBreakingNews()
                isScrolling = false
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}