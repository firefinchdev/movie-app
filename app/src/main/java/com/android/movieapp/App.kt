package com.android.movieapp

import android.app.Application
import coil.ImageLoader

class App: Application() {
    companion object {
        lateinit var instance: App
            private set

        lateinit var imageLoader: ImageLoader
            private set
    }
    override fun onCreate() {
        super.onCreate()
        instance = this
        imageLoader = ImageLoader.Builder(this)
            .build()
    }
}