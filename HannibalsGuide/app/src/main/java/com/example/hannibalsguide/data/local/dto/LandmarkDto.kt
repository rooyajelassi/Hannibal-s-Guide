package com.example.hannibalsguide.data.local.dto

import com.example.hannibalsguide.domain.model.Landmark

data class LandmarkDto(
    val id: String,
    val name: String,
    val city: String,
    val description: String,
    val history: String,
    val imageUrl: String,
    val images: List<String>,
    val lat: Double,
    val lng: Double,
    val category: String? = null
) {
    fun toDomain(): Landmark = Landmark(
        id = id,
        name = name,
        city = city,
        description = description,
        history = history,
        imageUrl = imageUrl,
        images = images,
        lat = lat,
        lng = lng,
        category = category ?: "Historical Site"
    )
}