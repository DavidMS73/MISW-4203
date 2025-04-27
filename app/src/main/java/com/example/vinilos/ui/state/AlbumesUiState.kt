package com.example.vinilos.ui.state

import com.example.vinilos.data.entities.Album
import com.example.vinilos.data.models.Response

data class AlbumesUiState (
    val albumesResponse: Response<List<Album>> = Response.Loading(),
    val albumSearchTerm: String = "",
)