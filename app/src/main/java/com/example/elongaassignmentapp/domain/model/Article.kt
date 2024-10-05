package com.example.elongaassignmentapp.domain.model

import com.google.gson.annotations.SerializedName

data class Article(
    @SerializedName("article_id") val articleId: String,
    val title: String? = null,
    val link: String? = null,
    val creator: List<String>? = null,
    val description: String? = null,
    val pubDate: String? = null,
    val imageUrl: String? = null,
    @SerializedName("source_name") val sourceName: String? = null,
    @SerializedName("source_icon") val sourceIcon: String? = null,
)
