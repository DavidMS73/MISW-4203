package com.example.vinilos.ui.state

import com.example.vinilos.data.entities.Performer
import com.example.vinilos.data.models.Response

data class PerformerDetailUiState (
    val performerDetailResponse: Response<Performer> = Response.Loading()
)