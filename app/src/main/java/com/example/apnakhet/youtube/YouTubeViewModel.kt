package com.example.apnakhet.youtube

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class YouTubeViewModel : ViewModel() {
        private val _videos = MutableStateFlow<List<YouTubeVideo>>(listOf(
            YouTubeVideo(VideoId("lDOkIrrTEhs")),
            YouTubeVideo(VideoId("8QO7YIvSQPc")),
            YouTubeVideo(VideoId("ilpcW_dn4lI")),
            YouTubeVideo(VideoId("L69l69ApLrw") ),
            YouTubeVideo(VideoId("1P8xirYNBbE"))

        )
        )
        val videos: StateFlow<List<YouTubeVideo>> get() = _videos


    }
