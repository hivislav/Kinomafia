package ru.kinomafia.model.repository

import ru.kinomafia.model.entities.FilmItem

interface Repository {
    fun getMostPopularMoviesFilmListFromServer(): List<FilmItem>
    fun getTop250FilmListFromServer(): List<FilmItem>
}