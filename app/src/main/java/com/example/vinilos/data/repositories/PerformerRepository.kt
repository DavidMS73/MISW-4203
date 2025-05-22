package com.example.vinilos.data.repositories

import com.example.vinilos.data.datasources.PerformerApiService
import com.example.vinilos.data.entities.Collector
import com.example.vinilos.data.entities.Performer

class PerformerRepository(
    private val serviceAdapter: PerformerApiService
) {
    suspend fun getPerformers(): List<Performer> {
        return serviceAdapter.getPerformers()
    }

    suspend fun getPerformersDetail(id: Int): Performer {
        return serviceAdapter.getPerformersDetail(id)
    }
}
