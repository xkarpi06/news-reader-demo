package com.example.elongaassignmentapp.data.repository

import com.example.elongaassignmentapp.BuildConfig
import com.example.elongaassignmentapp.data.api.NewsApi
import com.example.elongaassignmentapp.data.helper.handleApiCall
import com.example.elongaassignmentapp.domain.model.Article
import com.example.elongaassignmentapp.domain.model.Result
import retrofit2.await

interface NewsRepository {
    suspend fun fetchLatestNews(): Result<List<Article>>
}

class NewsRepositoryImpl(
    private val newsApi: NewsApi
) : NewsRepository {
    override suspend fun fetchLatestNews(): Result<List<Article>> {
        return handleApiCall {
            newsApi.getLatestNews(
                apiKey = BuildConfig.NEWS_API_KEY,
                question = "android",
                language = "en"
            ).await().results
        }
        // TODO: create class NewsResponse(totalResults, results[], nextPage)
    }
}
