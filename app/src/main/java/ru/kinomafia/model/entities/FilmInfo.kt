package ru.kinomafia.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FilmInfo(
    val id: String,
    val title:String,
    val year: String,
    val image: String,
    val runtimeStr: String,
    val plot: String,
    val directors: String,
    val stars: String,
    val genres: String
) : Parcelable
