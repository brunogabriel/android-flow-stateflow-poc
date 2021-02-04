package com.example.flowstateflowpoc.network

import com.example.flowstateflowpoc.network.models.PhotoResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface PhotoService {
    @GET("/photos")
    suspend fun getPhotos(): List<PhotoResponse>

    @GET("/photos")
    fun getPhotosRx(): Single<List<PhotoResponse>>
}