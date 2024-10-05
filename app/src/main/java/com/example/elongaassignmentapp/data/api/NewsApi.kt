package com.example.elongaassignmentapp.data.api

import com.example.elongaassignmentapp.data.model.NewsDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {

    @GET("api/1/latest")
    fun getLatestNews(
        @Query("apikey") apiKey: String,
        @Query("q") question: String,
        @Query("language") language: String = "cs",
    ): Call<NewsDto>

    @GET("api/1/latest")
    fun getArticleById(
        @Query("apikey") apiKey: String,
        @Query("id") articleId: String,
    ): Call<NewsDto>
}
