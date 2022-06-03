package ru.kinomafia.viewmodel

import ru.kinomafia.model.entities.FilmInfo
import ru.kinomafia.model.entities.FilmItem

sealed class AppState {
    data class Success(val filmDataNovelties: List<FilmItem>, val filmDataHits: List<FilmItem>): AppState()
    data class Error(val filmID: String): AppState()
    object Loading: AppState()
    data class SuccessLoadingFilmInfo (val filmInfo: FilmInfo) : AppState()
}