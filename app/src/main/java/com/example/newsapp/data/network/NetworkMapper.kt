package com.example.newsapp.data.network

import com.example.newsapp.R
import com.example.newsapp.data.network.model.ArticleNetworkEntity
import com.example.newsapp.data.network.model.NewsResponse
import com.example.newsapp.domain.model.Article
import com.example.newsapp.util.EntityMapper
import javax.inject.Inject

class NetworkMapper
@Inject
constructor(): EntityMapper<ArticleNetworkEntity, Article>{

    override fun mapFromEntity(entity: ArticleNetworkEntity): Article {
        return Article(
            author = entity.author ?: "Unknown",
            content = entity.content ?: "Unknown",
            description = entity.description ?: "Unknown",
            publishedAt = entity.publishedAt ?: "Unknown",
            source = entity.source,
            title = entity.title ?: "Unknown",
            url = entity.url ?: "Unknown",
            image = entity.urlToImage ?: "https://cdn.iconscout.com/icon/free/png-256/broken-image-1782063-1513075.png"
        )
    }

    override fun mapToEntity(domainModel: Article): ArticleNetworkEntity {
        return ArticleNetworkEntity(
            author = domainModel.author,
            content = domainModel.content,
            description = domainModel.description,
            publishedAt = domainModel.publishedAt,
            source = domainModel.source,
            title = domainModel.title,
            url = domainModel.url,
            urlToImage = domainModel.image
        )
    }

    fun mapFromEntityList(entities: NewsResponse): List<Article>{
        return entities.articles.map { mapFromEntity(it) }
    }
}