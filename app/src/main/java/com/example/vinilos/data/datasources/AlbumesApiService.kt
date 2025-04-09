package com.example.vinilos.data.datasources

import com.example.vinilos.data.entities.Album
import retrofit2.http.GET

interface AlbumesApiService {
    @GET("albums")
    suspend fun getAlbumes(): List<Album>
}
