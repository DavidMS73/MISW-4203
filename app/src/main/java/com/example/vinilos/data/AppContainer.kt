package com.example.vinilos.data

import com.example.vinilos.data.datasources.AlbumesApiService
import com.example.vinilos.data.datasources.mock.MockAlbumesApiService
import com.example.vinilos.data.repositories.AlbumesRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    // Retrofit Config
    private val BASE_URL =
        "http://localhost:3000"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Service Adapters
    private val albumesServiceAdapter: AlbumesApiService by lazy {
        retrofit.create(AlbumesApiService::class.java)
    }

    // Repositories
    val albumesRepository: AlbumesRepository by lazy {
        val mockAlbumesServiceAdapter = MockAlbumesApiService
        // TODO: Replace for real service adapter (albumesServiceAdapter)
        AlbumesRepository(mockAlbumesServiceAdapter)
    }
}