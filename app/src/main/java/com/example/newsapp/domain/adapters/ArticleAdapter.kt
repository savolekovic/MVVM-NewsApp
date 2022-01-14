package com.example.newsapp.domain.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.R
import com.example.newsapp.domain.model.Article
import kotlinx.android.synthetic.main.article_item.view.*
import javax.inject.Inject
import javax.inject.Singleton

class ArticleAdapter
    : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView)

    private val differCallback = object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem.url == newItem.url
        }

        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean {
            return oldItem == newItem
        }

    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.article_item,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val article = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(article.image).into(article_img)
            article_title.text = article.title
            article_author.text = article.author
            article_description.text = article.description
            setOnClickListener {
                onItemCLickListener?.let { it(article) }
            }
        }
    }

    private var onItemCLickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemCLickListener = listener
    }

}