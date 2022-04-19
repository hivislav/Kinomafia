package ru.kinomafia.model

interface Repository {
    fun getFilmInfoFromServer(): FilmInfo
    fun getFilmInfoFromLocalStorage(): FilmInfo

}