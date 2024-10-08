package com.example.elongaassignmentapp.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.elongaassignmentapp.data.api.NewsApi
import com.example.elongaassignmentapp.data.extension.toCallError
import com.example.elongaassignmentapp.domain.model.Article
import retrofit2.await

/**
 * PagingSource using Paging 3, loads Articles paginated
 *
 * For more information, see [Load and display paged data](https://developer.android.com/topic/libraries/architecture/paging/v3-paged-data)
 */
class NewsPagingSource(private val newsApi: NewsApi) : PagingSource<String, Article>() {

    override suspend fun load(params: LoadParams<String>): LoadResult<String, Article> {
        return try {
            val response = newsApi.getLatestNews(page = params.key).await()
            val articles = response.results
            val nextKey = if (articles.isNotEmpty()) response.nextPage else null
            LoadResult.Page(
                data = response.results,
                prevKey = params.key,
                nextKey = nextKey,
            )
        } catch (e: Exception) {
            LoadResult.Error(e.toCallError())
        }
    }

    // The refresh key is used for subsequent refresh calls to PagingSource.load after the initial load
    override fun getRefreshKey(state: PagingState<String, Article>): String? {
        // We need to get the previous key (or next key if previous is null) of the page
        // that was closest to the most recently accessed index.
        // Anchor position is the most recently accessed index
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey
                ?: state.closestPageToPosition(anchorPosition)?.nextKey
        }
    }
}
