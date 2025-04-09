package com.example.vinilos.data.repositories

import com.example.vinilos.data.datasources.AlbumesApiService
import com.example.vinilos.data.entities.Album

class AlbumesRepository(
    private val serviceAdapter: AlbumesApiService
) {
    suspend fun getAlbumes(): List<Album> {
        return serviceAdapter.getAlbumes()
    }
}
