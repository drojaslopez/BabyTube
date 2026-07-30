package com.babytube.player.ui.player

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.media3.common.MediaItem
import androidx.media3.common.Player
import com.babytube.player.data.model.VideoItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlayerViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(PlayerUiState())
    val uiState: StateFlow<PlayerUiState> = _uiState.asStateFlow()

    private var sleepTimerJob: Job? = null

    fun setVideo(video: VideoItem) {
        _uiState.value = _uiState.value.copy(
            currentVideo = video,
            mediaItem = MediaItem.fromUri(video.uri)
        )
    }

    fun toggleLock() {
        _uiState.value = _uiState.value.copy(
            isLocked = !_uiState.value.isLocked
        )
    }

    fun updateUnlockProgress(progress: Float) {
        _uiState.value = _uiState.value.copy(
            unlockProgress = progress
        )
    }

    fun resetUnlockProgress() {
        _uiState.value = _uiState.value.copy(
            unlockProgress = 0f
        )
    }

    fun toggleLoop() {
        _uiState.value = _uiState.value.copy(
            isLoopEnabled = !_uiState.value.isLoopEnabled
        )
    }

    fun setSleepTimer(minutes: Int?) {
        sleepTimerJob?.cancel()
        
        if (minutes != null) {
            sleepTimerJob = viewModelScope.launch {
                delay(minutes * 60_000L)
                _uiState.value = _uiState.value.copy(
                    shouldPause = true
                )
            }
            _uiState.value = _uiState.value.copy(
                sleepTimerMinutes = minutes
            )
        } else {
            _uiState.value = _uiState.value.copy(
                sleepTimerMinutes = null
            )
        }
    }

    fun clearPauseFlag() {
        _uiState.value = _uiState.value.copy(
            shouldPause = false
        )
    }

    override fun onCleared() {
        super.onCleared()
        sleepTimerJob?.cancel()
    }
}

data class PlayerUiState(
    val currentVideo: VideoItem? = null,
    val mediaItem: MediaItem? = null,
    val isLocked: Boolean = false,
    val unlockProgress: Float = 0f,
    val isLoopEnabled: Boolean = false,
    val sleepTimerMinutes: Int? = null,
    val shouldPause: Boolean = false
)
