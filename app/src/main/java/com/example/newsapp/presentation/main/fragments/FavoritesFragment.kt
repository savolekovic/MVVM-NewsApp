package com.example.newsapp.presentation.main.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.local.LocalMapper
import com.example.newsapp.databinding.FragmentFavoritesBinding
import com.example.newsapp.presentation.main.NewsAdapter
import com.example.newsapp.presentation.article_detail.ArticleDetailActivity
import com.example.newsapp.presentation.main.NewsActivity
import com.example.newsapp.presentation.main.NewsViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    @Inject
    lateinit var localMapper: LocalMapper

    private lateinit var binding: FragmentFavoritesBinding

    lateinit var viewModel: NewsViewModel

    @Inject
    @Named("favorites_news")
    lateinit var newsAdapter: NewsAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)

        viewModel = (activity as NewsActivity).viewModel

        setupRecycler()
        subscribeObservers()
        createSwipeToDelete()

        return binding.root
    }

    private fun createSwipeToDelete() {

        val itemTouchHelperCallback = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(
            0, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val article = newsAdapter.differ.currentList[position]
                viewModel.deleteArticle(article)
                Snackbar.make(binding.root, "Successfully deleted article", Snackbar.LENGTH_LONG)
                    .apply {
                        setAction("Undo") {
                            viewModel.saveArticle(article)
                        }
                    }.show()
            }
        })

        itemTouchHelperCallback.apply {
            attachToRecyclerView(binding.articlesRecycler)
        }
    }

    private fun subscribeObservers() {
        viewModel.getFavoriteArticles().observe(viewLifecycleOwner) {
            when (it.size) {
                0 -> binding.emptyListTv.visibility = View.VISIBLE
                else -> binding.emptyListTv.visibility = View.GONE
            }
            newsAdapter.differ.submitList(localMapper.mapFromEntityList(it))
        }
    }

    private fun setupRecycler() {
        newsAdapter.setOnItemClickListener {
            val intent = Intent(requireActivity(), ArticleDetailActivity::class.java)
            intent.putExtra("article", it)
            startActivity(intent)
        }
        binding.articlesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = newsAdapter
        }
    }
}