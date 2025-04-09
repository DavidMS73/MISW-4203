package com.example.vinilos

import android.app.Application
import com.example.vinilos.data.AppContainer

class VinilosApplication: Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppContainer()
    }
}