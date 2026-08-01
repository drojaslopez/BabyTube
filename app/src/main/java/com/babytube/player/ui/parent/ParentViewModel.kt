package com.babytube.player.ui.parent

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.babytube.player.data.model.VideoItem
import com.babytube.player.data.repository.VideoRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ParentViewModel @Inject constructor(
    private val videoRepository: VideoRepository
) : ViewModel() {
    
    private val _allVideos = MutableStateFlow<List<VideoItem>>(emptyList())
    val allVideos: StateFlow<List<VideoItem>> = _allVideos.asStateFlow()

    private val _whitelistedIds = MutableStateFlow<Set<String>>(emptySet())
    val whitelistedIds: StateFlow<Set<String>> = _whitelistedIds.asStateFlow()

    init {
        loadVideos()
    }

    private fun loadVideos() {
        viewModelScope.launch {
            videoRepository.getLocalVideos().onSuccess { videos ->
                _allVideos.value = videos
            }
            _whitelistedIds.value = videoRepository.getWhitelistedIds()
        }
    }

    fun toggleVideo(id: Long, isWhitelisted: Boolean) {
        videoRepository.toggleWhitelist(id, isWhitelisted)
        _whitelistedIds.value = videoRepository.getWhitelistedIds()
    }
}
