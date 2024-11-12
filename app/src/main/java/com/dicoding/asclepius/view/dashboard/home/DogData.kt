package com.dicoding.asclepius.view.dashboard.home

import com.dicoding.asclepius.R

// DogData.kt
data class DogData(
    val id: Int,
    val imageUrl: Int,  // URL gambar atau resource drawable
    val country: String,
    val classification: String,
    val rating: Double,
    val reviews: Int,
    val isFavorite: Boolean = false
)

// DummyData.kt
object DummyData {
    val dogList = listOf(
        DogData(
            id = 1,
            imageUrl = R.drawable.img,
            country = "England",
            classification = "Cocker Spaniel",
            rating = 4.8,
            reviews = 143
        ),
        DogData(
            id = 2,
            imageUrl = R.drawable.img_1,
            country = "Siberia",
            classification = "Siberian Husky",
            rating = 4.9,
            reviews = 167
        ),
        DogData(
            id = 3,
            imageUrl = R.drawable.img_2,
            country = "Scotland",
            classification = "Golden Retriever",
            rating = 5.0,
            reviews = 189
        ),
        DogData(
            id = 4,
            imageUrl = R.drawable.img_3,
            country = "Germany",
            classification = "German Shepherd",
            rating = 4.7,
            reviews = 156
        ),
        DogData(
            id = 5,
            imageUrl = R.drawable.img_4,
            country = "France",
            classification = "Standard Poodle",
            rating = 4.6,
            reviews = 132
        )
    )
}