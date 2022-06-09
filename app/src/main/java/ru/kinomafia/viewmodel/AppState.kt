package ru.kinomafia.viewmodel

import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO

sealed class AppState {
    data class Success(val filmItemListTop250: List<FilmItem>, val filmItemListMostPopular: List<FilmItem>): AppState()
    data class Error(val filmID: String): AppState()
    object Loading: AppState()
    data class SuccessLoadingFilmInfo (val filmInfoDTO: FilmInfoDTO) : AppState()
}