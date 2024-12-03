package ru.nsu.contact.application.data.entity

data class BannerEntity(
    val id: Long,
    val category: String,
    val title: String,
    val description: String,
    val imageUrl: String
)
