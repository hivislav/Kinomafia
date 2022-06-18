package ru.kinomafia.model.repository

import ru.kinomafia.model.entities.FilmInfo

interface RepositoryLocal {
    fun getAllFavouriteFilms(): List<FilmInfo>
    fun saveFavouriteFilm(filmInfo: FilmInfo)
}