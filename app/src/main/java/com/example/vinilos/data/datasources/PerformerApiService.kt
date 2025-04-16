package com.example.vinilos.data.datasources

import com.example.vinilos.data.entities.Performer
import retrofit2.http.GET

interface PerformerApiService {

    @GET("musicians")
    suspend fun getPerformers(): List<Performer>
}