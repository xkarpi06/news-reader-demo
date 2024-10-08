package com.example.elongaassignmentapp.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.elongaassignmentapp.data.api.NewsApi
import com.example.elongaassignmentapp.data.helper.handleApiCall
import com.example.elongaassignmentapp.data.paging.NewsPagingSource
import com.example.elongaassignmentapp.domain.model.Article
import com.example.elongaassignmentapp.domain.model.Result
import kotlinx.coroutines.flow.Flow
import retrofit2.await

interface NewsRepository {
    fun getLatestNewsFlow(): Flow<PagingData<Article>>
    suspend fun fetchArticleById(articleId: String): Result<Article>
}

class NewsRepositoryImpl(private val newsApi: NewsApi) : NewsRepository {
    /**
     *  Paging 3 flow creator
     *
     *  Paging 3
     *   - handles in-memory cache,
     *   - requests data when the user is close to the end of the list.
     */
    override fun getLatestNewsFlow(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, // Free newsapi.io plan limits page size to 10 max, higher numbers yield error response
                enablePlaceholders = false, // Set true to return nulls and display placeholder items
            ),
            pagingSourceFactory = { NewsPagingSource(newsApi) }
        ).flow
    }

    override suspend fun fetchArticleById(articleId: String): Result<Article> {
        return handleApiCall {
            newsApi.getArticleById(articleId)
                .await()
                .results.first()
        }
    }
}
