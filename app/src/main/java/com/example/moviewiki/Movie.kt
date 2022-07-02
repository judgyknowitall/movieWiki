package com.example.moviewiki


data class Movie (
    val title: String = "Title",
    val imageId: Int = R.drawable.profile_picture,
    val crew: List<String> = emptyList(),
    val cast: List<String> = emptyList(),
    val description: String = "Description"
)