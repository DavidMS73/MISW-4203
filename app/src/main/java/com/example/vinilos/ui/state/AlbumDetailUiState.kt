package com.example.vinilos.ui.state

import com.example.vinilos.data.entities.Album
import com.example.vinilos.data.models.Response

data class AlbumDetailUiState (
    val albumDetailResponse: Response<Album> = Response.Loading(),
)