package com.example.apnakhet.Weather

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

// Weather API Interface
interface WeatherApi {
    @GET("weather")
    suspend fun getWeather(
        @Query("q") city: String,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

//    yt
    companion object{
        private const val BASE_URL = "https://api.openweathermap.org/data/2.5/"

        fun create(): WeatherApi{
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(WeatherApi::class.java)

        }

    }

}

// Retrofit instance
//object RetrofitInstance {
//    val api: WeatherApi by lazy {
//        Retrofit.Builder()
//            .baseUrl("https://api.openweathermap.org/data/2.5/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(WeatherApi::class.java)
//    }
//}
