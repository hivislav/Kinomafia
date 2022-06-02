package ru.kinomafia.model

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

    fun getDurationFilmInString(duration: Int): String {
        val hours: Int = duration / 60
        val minutes: Int = duration - 60 * hours
        return ("$hours ч. $minutes мин.")
    }

    fun getDefaultFilm() = FilmItem("0", "Терминатор 2: Судный день",
        "1991", R.drawable.terminator2poster.toString(), "9.2")

    fun getNoveltiesFilms() = listOf(
        getDefaultFilm()
    )

    fun getHitsFilms() = listOf(
        getDefaultFilm()
    )