package com.example.newsapp.data.local

import com.example.newsapp.data.network.ArticleNetworkEntity
import com.example.newsapp.data.network.ArticlesResponse
import com.example.newsapp.domain.entities.Article
import com.example.newsapp.util.EntityMapper
import javax.inject.Inject

class LocalMapper
@Inject
constructor(): EntityMapper<ArticleLocalEntity, Article>{
    override fun mapFromEntity(entity: ArticleLocalEntity): Article {
        return Article(
            author = entity.author ?: "Unknown",
            content = entity.content ?: "Unknown",
            description = entity.description ?: "Unknown",
            publishedAt = entity.publishedAt ?: "Unknown",
            source = entity.source,
            title = entity.title ?: "Unknown",
            url = entity.url ?: "Unknown",
            image = entity.image ?: "https://cdn.iconscout.com/icon/free/png-256/broken-image-1782063-1513075.png"
        )
    }

    override fun mapToEntity(domainModel: Article): ArticleLocalEntity {
        return ArticleLocalEntity(
            author = domainModel.author,
            content = domainModel.content,
            description = domainModel.description,
            publishedAt = domainModel.publishedAt,
            source = domainModel.source,
            title = domainModel.title,
            url = domainModel.url,
            image = domainModel.image
        )
    }

    fun mapFromEntityList(entities: List<ArticleLocalEntity>): List<Article>{
        return entities.map { mapFromEntity(it) }
    }
}