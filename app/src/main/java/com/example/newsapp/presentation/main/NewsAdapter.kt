package com.example.newsapp.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ArticleItemBinding
import com.example.newsapp.domain.entities.ArticleDomainEntity

class NewsAdapter : RecyclerView.Adapter<NewsAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(private val itemBinding: ArticleItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {

        fun bind(article: ArticleDomainEntity, onItemCLickListener: ((ArticleDomainEntity) -> Unit)?) {
            itemBinding.apply {
                Glide.with(root).load(article.image).into(articleImg)
                articleTitle.text = article.title
                val sourceString = "Source: ${article.author}"
                articleAuthor.text = sourceString
                root.setOnClickListener {
                    onItemCLickListener?.let { it(article) }
                }
            }
        }

        companion object {
            fun from(parent: ViewGroup): ArticleViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val itemBinding = ArticleItemBinding.inflate(layoutInflater, parent, false)
                return ArticleViewHolder(itemBinding)
            }
        }
    }

    private val differCallback = object : DiffUtil.ItemCallback<ArticleDomainEntity>() {
        override fun areItemsTheSame(oldItem: ArticleDomainEntity, newItem: ArticleDomainEntity): Boolean =
            oldItem.url == newItem.url

        override fun areContentsTheSame(oldItem: ArticleDomainEntity, newItem: ArticleDomainEntity): Boolean =
            oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(differ.currentList[position], onItemCLickListener)
    }

    private var onItemCLickListener: ((ArticleDomainEntity) -> Unit)? = null

    fun setOnItemClickListener(listener: (ArticleDomainEntity) -> Unit) {
        onItemCLickListener = listener
    }

}