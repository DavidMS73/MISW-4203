package com.example.vinilos.ui.state

import com.example.vinilos.data.entities.Collector
import com.example.vinilos.data.models.Response

data class CollectorsUiState(
    val collectorsResponse: Response<List<Collector>> = Response.Loading(),
    val collectorSearchTerm: String = "",
)