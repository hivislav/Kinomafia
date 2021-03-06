package ru.kinomafia.model.entities.rest_entities

data class FilmInfoDTO(
    val id: String,
    val title:String,
    val year: String,
    val image: String,
    val runtimeStr: String,
    val plot: String,
    val directors: String,
    val stars: String,
    val genres: String
)
