package com.example.apnakhet.youtube


import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface YouTubeApiService {
    @GET("search")
    suspend fun searchVideos(
        @Query("part") part: String = "snippet",
        @Query("q") query: String,
        @Query("key") apiKey: String,
        @Query("type") type: String = "video",
        @Query("maxResults") maxResults: Int = 10
    ): Response<YouTubeSearchResponse>
}
