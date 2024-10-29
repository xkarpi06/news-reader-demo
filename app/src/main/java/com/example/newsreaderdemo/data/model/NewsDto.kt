package com.example.newsreaderdemo.data.model

import com.example.newsreaderdemo.domain.model.Article

data class NewsDto(
    val results: List<Article>,
    val nextPage: String?,
)
