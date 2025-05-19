package com.example.vinilos.data.datasources

import com.example.vinilos.data.entities.Album
import com.example.vinilos.data.entities.Collector
import retrofit2.http.GET
import retrofit2.http.Path

interface CollectorApiService {
    @GET("collectors")
    suspend fun getCollectors(): List<Collector>

    @GET("/collectors/{id}")
    suspend fun getCollectorsDetail(@Path("id") id: Int): Collector
}