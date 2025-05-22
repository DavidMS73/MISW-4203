package com.example.vinilos.data.datasources

import com.example.vinilos.data.entities.Performer
import retrofit2.http.GET
import retrofit2.http.Path

interface PerformerApiService {
    @GET("musicians")
    suspend fun getPerformers(): List<Performer>

    @GET("musicians/{id}")
    suspend fun getPerformersDetail(@Path("id") id: Int): Performer
}