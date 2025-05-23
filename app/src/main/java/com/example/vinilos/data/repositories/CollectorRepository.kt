package com.example.vinilos.data.repositories

import com.example.vinilos.data.datasources.CollectorApiService
import com.example.vinilos.data.entities.Album
import com.example.vinilos.data.entities.Collector

class CollectorRepository(
    private val collectorApiService: CollectorApiService
) {
    suspend fun getCollectors(): List<Collector> {
        return collectorApiService.getCollectors()
    }

    suspend fun getCollectorDetail(id: Int): Collector {
        return collectorApiService.getCollectorsDetail(id)
    }
}