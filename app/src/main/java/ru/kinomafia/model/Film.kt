package ru.kinomafia.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Film(
    val poster: Int,
    val name: String,
    val genre: String,
    val duration: Int,
    val year: Int) : Parcelable
{
    fun getDurationFilmInString(duration: Int): String {
        val hours: Int = duration / 60
        val minutes: Int = duration - 60 * hours
        return ("$hours ч. $minutes мин.")
    }
}




