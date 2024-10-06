package com.example.elongaassignmentapp.di

import com.example.elongaassignmentapp.BuildConfig
import com.example.elongaassignmentapp.data.api.NewsApi
import com.google.gson.GsonBuilder
import com.google.gson.JsonDeserializer
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val networkModule = module {
    single {
        // Create a logging interceptor
        val loggingInterceptor = HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        }

        // Create OkHttpClient with the logging interceptor
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .build()
    }

    single {
        // Create gson instance that can parse LocalDateTime
        val gson = GsonBuilder()
            .registerTypeAdapter(LocalDateTime::class.java, JsonDeserializer { json, _, _ ->
                LocalDateTime.parse(json.asString, DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
            })
            .create()

        // Create Retrofit instance
        Retrofit.Builder()
            .baseUrl("https://newsdata.io")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    single<NewsApi> {
        // Create API service
        val retrofit: Retrofit = get()
        retrofit.create(NewsApi::class.java)
    }
}
