package com.example.apnakhet.youtube

data class YouTubeSearchResponse(
    val items: List<YouTubeVideo>
)

data class YouTubeVideo(
    val id: VideoId,

)

data class VideoId(
    val videoId: String
)

