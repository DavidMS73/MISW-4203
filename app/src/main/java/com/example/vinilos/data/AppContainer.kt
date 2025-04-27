package com.example.vinilos.data

import com.example.vinilos.data.datasources.AlbumesApiService
import com.example.vinilos.data.datasources.PerformerApiService
import com.example.vinilos.data.datasources.mock.MockAlbumesApiService
import com.example.vinilos.data.repositories.AlbumesRepository
import com.example.vinilos.data.repositories.PerformerRepository
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AppContainer {
    // Retrofit Config
    private val BASE_URL =
        "https://vynilsbe.onrender.com"
    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Service Adapters
    private val albumesServiceAdapter: AlbumesApiService by lazy {
        retrofit.create(AlbumesApiService::class.java)
    }

    private val performersServiceAdapter: PerformerApiService by lazy {
        retrofit.create(PerformerApiService::class.java)
    }

    // Repositories
    val albumesRepository: AlbumesRepository by lazy {
        AlbumesRepository(albumesServiceAdapter)
    }

    // Repositories
    val performersRepository: PerformerRepository by lazy {
        PerformerRepository(performersServiceAdapter)
    }
}