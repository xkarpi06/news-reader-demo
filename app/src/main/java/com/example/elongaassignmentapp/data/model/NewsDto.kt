package com.example.elongaassignmentapp.data.model

import com.example.elongaassignmentapp.domain.model.Article

data class NewsDto(
    val results: List<Article>,
    val nextPage: String?,
)
