package ru.kinomafia.model.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favourite_film_entity")
data class FavouriteFilmEntity(
    @PrimaryKey
    val id: String,
    val title:String,
    val year: String,
    val image: String,
    val runtimeStr: String,
    val plot: String,
    val directors: String,
    val stars: String,
    val genres: String,
    val note: String
)
