package com.example.flowstateflowpoc.shared

import android.app.Application
import com.example.flowstateflowpoc.network.RetrofitApp

class POCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        RetrofitApp.initialize(baseContext)
    }
}