package com.example.flowstateflowpoc.network

import android.content.Context
import com.chuckerteam.chucker.api.ChuckerCollector
import com.chuckerteam.chucker.api.ChuckerInterceptor
import com.chuckerteam.chucker.api.RetentionManager
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitApp {

    private lateinit var retrofit: Retrofit

    fun initialize(context: Context) {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(provideChuckerInterceptor(context))
            .build()

        retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideChuckerInterceptor(context: Context): Interceptor {
        val collector = ChuckerCollector(
            context = context,
            showNotification = true,
            retentionPeriod = RetentionManager.Period.FOREVER
        )

        return ChuckerInterceptor.Builder(context)
            .collector(collector)
            .alwaysReadResponseBody(true)
            .build()
    }

    fun <T> provideService(clazz: Class<T>): T = retrofit.create(clazz)
}