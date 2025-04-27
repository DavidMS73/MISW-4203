package com.example.vinilos.data.entities

data class Collector (
    val id: Int,
    val name: String,
    val telephone: String,
    val email: String,
    val favoritePerformers: List<Performer>,
    val comments: List<Comment>,
    val collectorAlbums: List<CollectorAlbum>,
    )
