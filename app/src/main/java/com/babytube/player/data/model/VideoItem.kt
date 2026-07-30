package com.babytube.player.data.model

data class VideoItem(
    val id: Long,
    val uri: String,
    val name: String,
    val duration: Long,
    val size: Long,
    val mimeType: String
)
