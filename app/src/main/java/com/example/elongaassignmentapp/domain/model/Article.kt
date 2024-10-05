package com.example.elongaassignmentapp.domain.model

data class Article(
    val articleId: String,
    val title: String? = null,
    val creator: String? = null,
    val description: String? = null,
    val pubDate: String? = null,
    val imageUrl: String? = null,
    val sourceName: String? = null,
    val sourceIcon: String? = null,
    val language: String? = null,
)
