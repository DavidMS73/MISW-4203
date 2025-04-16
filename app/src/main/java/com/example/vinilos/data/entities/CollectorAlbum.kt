package com.example.vinilos.data.entities

data class CollectorAlbum (
    val id: Int,
    val price: Int,
    val status: AlbumStatus,
    val album: Album,
    val collector: Collector
)