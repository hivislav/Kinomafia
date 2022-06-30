package ru.kinomafia.model.repository

import retrofit2.Callback
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO
import ru.kinomafia.model.entities.rest_entities.FilmItemListDTO

interface RepositoryRemote {
    fun getFilmInfo(id: String, callback: Callback<FilmInfoDTO>)
    fun getTop250FilmListFromServer(callback: Callback<FilmItemListDTO>)
    fun getMostPopularMoviesFilmListFromServer(callback: Callback<FilmItemListDTO>)
    fun getFilmListsByRateFromServer(): List<List<FilmItem>>
}