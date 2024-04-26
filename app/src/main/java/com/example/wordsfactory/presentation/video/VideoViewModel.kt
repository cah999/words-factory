package com.example.wordsfactory.presentation.video

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class VideoViewModel : ViewModel() {
    private val _videoState = MutableStateFlow(VideoState())
    val videoState = _videoState.asStateFlow()

    fun onProgressChanged(progress: Int) {
        _videoState.update { it.copy(progress = progress) }
    }

    fun onBackEnabledChanged(backEnabled: Boolean) {
        _videoState.update { it.copy(backEnabled = backEnabled) }
    }
}

data class VideoState(
    val progress: Int = 0, val backEnabled: Boolean = false
)