package com.example.elongaassignmentapp.data.model

import com.example.elongaassignmentapp.domain.model.Article

data class NewsDto(
    val status: String,
    val totalResults: Int,
    val results: List<Article>
)
