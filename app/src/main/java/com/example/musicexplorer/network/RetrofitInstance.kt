package com.example.musicexplorer.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    // Add logging interceptor for debugging
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("https://ws.audioscrobbler.com/")  // Include /2.0/ in base URL
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: LastFmApiService by lazy {
        retrofit.create(LastFmApiService::class.java)
    }
}