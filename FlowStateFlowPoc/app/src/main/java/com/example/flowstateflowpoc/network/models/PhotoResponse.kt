package com.example.flowstateflowpoc.network.models

data class PhotoResponse(
    val albumId: Long,
    val id: Long,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)