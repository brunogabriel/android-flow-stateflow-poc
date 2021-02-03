package com.example.flowstateflowpoc.mvp.domain.mapper

import com.example.flowstateflowpoc.network.models.PhotoResponse
import com.example.flowstateflowpoc.shared.adapter.models.Photo

fun PhotoResponse.toMVPPhoto() = Photo(
    title,
    thumbnailUrl
)