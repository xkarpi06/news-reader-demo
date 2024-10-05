package com.example.elongaassignmentapp.data.repository

import com.example.elongaassignmentapp.domain.model.Article

interface NewsRepository {
    suspend fun fetchLatestNews(): List<Article>
}

class NewsRepositoryImpl : NewsRepository {
    override suspend fun fetchLatestNews(): List<Article> {
        // Fetch the latest news here
        return listOf() // TODO: implement
    }
}
