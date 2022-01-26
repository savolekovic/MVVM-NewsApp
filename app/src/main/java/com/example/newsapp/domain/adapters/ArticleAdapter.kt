package com.example.newsapp.domain.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.newsapp.databinding.ArticleItemBinding
import com.example.newsapp.domain.model.Article
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ArticleAdapter
    @Inject
    constructor()
    : RecyclerView.Adapter<ArticleAdapter.ArticleViewHolder>() {

    class ArticleViewHolder(private val itemBinding: ArticleItemBinding)
        : RecyclerView.ViewHolder(itemBinding.root){

        fun bind(article: Article, onItemCLickListener: ((Article) -> Unit)?){
            itemBinding.apply {
                Glide.with(root).load(article.image).into(articleImg)
                articleTitle.text = article.title
                articleAuthor.text = article.author
                articleDescription.text = article.description
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

    private val differCallback = object: DiffUtil.ItemCallback<Article>(){
        override fun areItemsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem.url == newItem.url
        override fun areContentsTheSame(oldItem: Article, newItem: Article): Boolean = oldItem == newItem
    }

    val differ = AsyncListDiffer(this, differCallback)

    override fun getItemCount(): Int = differ.currentList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        holder.bind(differ.currentList[position], onItemCLickListener)
    }

    private var onItemCLickListener: ((Article) -> Unit)? = null

    fun setOnItemClickListener(listener: (Article) -> Unit){
        onItemCLickListener = listener
    }

}