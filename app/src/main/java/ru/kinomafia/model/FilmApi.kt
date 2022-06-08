package ru.kinomafia.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO
import ru.kinomafia.model.entities.rest_entities.FilmItemListDTO

interface FilmApi {

    @GET(IMDB_TOP_250_MOVIES_END_POINT)
    fun getFilmItemListTop250(@Query("apiKey") apiKey: String = API_KEY): Call<FilmItemListDTO>

    @GET(IMDB_MOST_POPULAR_MOVIES_END_POINT)
    fun getFilmItemListMostPopular(@Query("apiKey") apiKey: String = API_KEY): Call<FilmItemListDTO>

    @GET(IMDB_GET_TITLE_END_POINT)
    fun getFilmInfo(@Query("apiKey") apiKey: String = API_KEY, @Query("id") filmID: String): Call<FilmInfoDTO>
}