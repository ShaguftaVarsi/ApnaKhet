package com.example.apnakhet.youtube
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "https://www.googleapis.com/youtube/v3/"

    val instance: YouTubeApiService by lazy {
        val retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()

                    retrofit.create(YouTubeApiService::class.java)
                }
            }
