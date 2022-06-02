package ru.kinomafia.model

interface Repository {
    fun getFilmInfoFromServer(): FilmItem
    fun getFilmInfoFromLocalStorageNovelties(): List<FilmItem>
    fun getFilmInfoFromLocalStorageHits(): List<FilmItem>
}