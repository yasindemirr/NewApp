package com.demir.news.view.view.model

import com.demir.news.view.view.model.Article

data class NewsResponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)