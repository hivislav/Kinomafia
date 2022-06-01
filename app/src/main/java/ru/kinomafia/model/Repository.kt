package ru.kinomafia.model

interface Repository {
    fun getFilmInfoFromServer(): FilmInfo
    fun getFilmInfoFromLocalStorageNovelties(): List<FilmInfo>
    fun getFilmInfoFromLocalStorageHits(): List<FilmInfo>
}