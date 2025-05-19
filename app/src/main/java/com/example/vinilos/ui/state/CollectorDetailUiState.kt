package com.example.vinilos.ui.state

import com.example.vinilos.data.entities.Collector
import com.example.vinilos.data.models.Response

data class CollectorDetailUiState (
    val collectorDetailResponse: Response<Collector> = Response.Loading(),
)