package com.example.hannibalsguide.domain.model

data class Landmark(
    val id: String,
    val name: String,
    val city: String,
    val description: String,
    val history: String,
    val imageUrl: String,
    val images: List<String>,
    val lat: Double,
    val lng: Double,
    val category: String = "Historical Site"
)