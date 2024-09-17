package com.example.apnakhet.Weather

data class WeatherResponse(
    val name: String,
    val main: Main,

    val weather: List<Weather>,
//    val wind: Wind

)

data class Main(
    val temp: Float,
    val temp_min: Float,
    val temp_max: Float,
    val humidity: Int
)

data class Weather(
//    val main: String,
    val description: String
)

//data class Wind(
//    val speed: Float
//)


