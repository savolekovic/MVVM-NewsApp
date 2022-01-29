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
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.newsapp.R
import com.example.newsapp.data.local.LocalMapper
import com.example.newsapp.databinding.FragmentFavoritesBinding
import com.example.newsapp.domain.adapters.ArticleAdapter
import com.example.newsapp.domain.entities.Article
import com.example.newsapp.presentation.article_detail.ArticleDetailActivity
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class FavoritesFragment : Fragment(R.layout.fragment_favorites) {

    @Inject
    lateinit var localMapper: LocalMapper

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

        displayProgressBar(false)
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
                val article = articleAdapter.differ.currentList[position]
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
        viewModel.getFavoriteArticles().observe(this, {
            articleAdapter.differ.submitList(localMapper.mapFromEntityList(it))
        })
    }

    private fun setupRecycler() {
        articleAdapter.setOnItemClickListener {
            val intent = Intent(requireActivity(), ArticleDetailActivity::class.java)
            intent.putExtra("article", it)
            startActivity(intent)
        }
        binding.articlesRecycler.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = articleAdapter
        }
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