package ru.kinomafia.model.entities

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.kinomafia.R

@Parcelize
data class FilmItem(
    val id: String,
    val title:String,
    val year: String,
    val image: String,
    val imDbRating: String
) : Parcelable