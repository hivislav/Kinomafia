package ru.kinomafia.model.repository

import ru.kinomafia.model.entities.FilmInfo
import ru.kinomafia.model.entities.FilmItem

interface Repository {
    fun getFilmInfo(id: String): FilmInfo
    fun getMostPopularMoviesFilmListFromServer(): List<FilmItem>
    fun getTop250FilmListFromServer(): List<FilmItem>
}