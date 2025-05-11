package com.example.vinilos.data.datasources

import com.example.vinilos.data.entities.Album
import retrofit2.http.GET
import retrofit2.http.Path

interface AlbumesApiService {
    @GET("albums")
    suspend fun getAlbumes(): List<Album>

    @GET("/albums/{id}")
    suspend fun getAlbumDetail(@Path("id") id: Int): Album
}
