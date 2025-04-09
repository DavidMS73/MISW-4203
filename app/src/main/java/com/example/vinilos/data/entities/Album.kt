package com.example.vinilos.data.entities

data class Album(
    val id: Int,
    val name: String,
    val cover: String,
    val releaseDate: String,
    val description: String,
    val genre: Genre,
    val recordLabel: RecordLabel,
    val tracks: List<Track>,
    val performers: List<Performer>,
    val comments: List<Comment>,
)
