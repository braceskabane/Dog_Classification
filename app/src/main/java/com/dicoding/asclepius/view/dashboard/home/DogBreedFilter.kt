package com.dicoding.asclepius.view.dashboard.home

// Model untuk breed filter
data class DogBreedFilter(
    val name: String,
    var isSelected: Boolean = false
)

// Data dummy yang lebih sederhana
object DummyDogBreeds {
    val breeds = listOf(
        DogBreedFilter("Chihuahua"),
        DogBreedFilter("Japanese Spaniel"),
        DogBreedFilter("Maltese Dog"),
        DogBreedFilter("Pekinese"),
        DogBreedFilter("Shih-Tzu"),  // Default selected
        DogBreedFilter("Blenheim Spaniel"),
        DogBreedFilter("Papillon"),
        DogBreedFilter("Toy Terrier"),
        DogBreedFilter("Rhodesian Ridgeback"),
        DogBreedFilter("Afghan Hound")
    )
}