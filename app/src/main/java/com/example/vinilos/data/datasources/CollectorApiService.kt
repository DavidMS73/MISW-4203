package com.example.vinilos.data.datasources

import com.example.vinilos.data.entities.Collector
import retrofit2.http.GET

interface CollectorApiService {
    @GET("collectors")
    suspend fun getCollectors(): List<Collector>
}