package com.example.vinilos.ui.state

import com.example.vinilos.data.entities.Performer
import com.example.vinilos.data.models.Response

data class PerformersUiState (
    val performersResponse: Response<List<Performer>> = Response.Loading(),
    val performerSearchTerm: String = "",
)