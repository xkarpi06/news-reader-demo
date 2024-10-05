package com.example.elongaassignmentapp.di

import com.example.elongaassignmentapp.BuildConfig
import com.example.elongaassignmentapp.data.api.NewsApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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
        // Create Retrofit instance
        Retrofit.Builder()
            .baseUrl("https://newsdata.io")
            .client(get<OkHttpClient>())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<NewsApi> {
        // Create API service
        val retrofit: Retrofit = get()
        retrofit.create(NewsApi::class.java)
    }
}
