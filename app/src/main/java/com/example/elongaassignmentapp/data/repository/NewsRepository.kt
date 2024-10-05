package com.example.elongaassignmentapp.data.repository

import com.example.elongaassignmentapp.data.api.NewsApi
import com.example.elongaassignmentapp.domain.model.Article
import com.example.elongaassignmentapp.BuildConfig
import retrofit2.await

interface NewsRepository {
    suspend fun fetchLatestNews(): List<Article>
}

class NewsRepositoryImpl(
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun fetchLatestNews(): List<Article> {
        return try {
            // Make the API call and await the response
            val call = newsApi.getLatestNews(
                apiKey = BuildConfig.NEWS_API_KEY,
                question = "politika",
                language = "cs"
            )
            val response = call.await()
            response.results
        } catch (e: Exception) {
            // Handle the exception
            emptyList() // or throw an exception or handle error accordingly
        }
    }
}
