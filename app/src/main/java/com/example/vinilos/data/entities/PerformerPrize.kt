package com.example.vinilos.data.entities

data class PerformerPrize(
    val id: Int,
    val premiationDate: String,
    val prize: Prize? = null,
    val performer: Performer? = null
)
