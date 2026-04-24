package com.example.hannibalsguide.data

import com.example.hannibalsguide.domain.model.Landmark
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LocalDataSource @Inject constructor() {

    fun getLandmarks(): List<Landmark> = listOf(
        Landmark(
            id = "1",
            name = "El Jem Amphitheatre",
            city = "El Jem",
            description = "A massive Roman amphitheatre and one of Tunisia’s most iconic monuments.",
            history = "Built around the 3rd century AD, it hosted gladiator games.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/7/7e/El_Jem_Amphitheatre.jpg",
            images = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/7/7e/El_Jem_Amphitheatre.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/0/0e/El_Djem_2.jpg"
            ),
            lat = 35.2964,
            lng = 10.7069
        ),
        Landmark(
            id = "2",
            name = "Carthage Ruins",
            city = "Tunis",
            description = "Ancient archaeological site of the Punic and Roman city of Carthage.",
            history = "Founded in the 9th century BC, later rebuilt by Romans.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/5/5b/Carthage_Thermes_d%27Antonin.jpg",
            images = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/5/5b/Carthage_Thermes_d%27Antonin.jpg",
                "https://upload.wikimedia.org/wikipedia/commons/3/3f/Carthage_ruins.jpg"
            ),
            lat = 36.8529,
            lng = 10.3230
        ),
        Landmark(
            id = "3",
            name = "Ribat of Monastir",
            city = "Monastir",
            description = "Historic Islamic fortress overlooking the Mediterranean.",
            history = "Dates back to the 8th century and served defensive and religious roles.",
            imageUrl = "https://upload.wikimedia.org/wikipedia/commons/9/9d/Ribat_Monastir.jpg",
            images = listOf(
                "https://upload.wikimedia.org/wikipedia/commons/9/9d/Ribat_Monastir.jpg"
            ),
            lat = 35.7778,
            lng = 10.8262
        )
    )
}