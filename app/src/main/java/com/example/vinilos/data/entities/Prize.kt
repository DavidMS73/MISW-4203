package com.example.vinilos.data.entities

data class Prize(
    val id: Int,
    val organization: String,
    val name: String,
    val description: String,
    val performerPrize: List<PerformerPrize>
)
