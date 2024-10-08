package com.example.elongaassignmentapp.data.api

import com.example.elongaassignmentapp.data.model.NewsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    /**
     * Returns latest news by provided question and language
     *
     * Response codes:
     *  x 200: Successful operation
     *  x 401: Unauthorized
     *  x 429: Too many requests - exceeded the rate limit for your plan
     *  x 500: Internal server error
     */
    @GET("api/1/latest")
    fun getLatestNews(
        @Query("q") question: String = "android",
        @Query("language") language: String = "en",
        @Query("page") page: String? = null, // If page is null, this query parameter will be omitted by Retrofit
    ): Call<NewsDto>

    @GET("api/1/latest")
    fun getArticleById(
        @Query("id") articleId: String,
    ): Call<NewsDto>
}
