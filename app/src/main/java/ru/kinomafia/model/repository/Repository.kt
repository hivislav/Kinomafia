package ru.kinomafia.model.repository

import retrofit2.Callback
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO

interface Repository {
    fun getFilmInfo(id: String, callback: Callback<FilmInfoDTO>)
    fun getTop250FilmListFromServer() : List<FilmItem>
    fun getMostPopularMoviesFilmListFromServer() : List<FilmItem>
    fun getFilmListsByRateFromServer(): List<List<FilmItem>>
}