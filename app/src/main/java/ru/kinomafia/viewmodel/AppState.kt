package ru.kinomafia.viewmodel

import ru.kinomafia.model.entities.FilmInfo
import ru.kinomafia.model.entities.FilmItem

sealed class AppState {
    data class Success(val filmDataNovelties: List<FilmItem>, val filmDataHits: List<FilmItem>): AppState()
    object Error: AppState()
    object Loading: AppState()
    data class SuccessLoadingFilmInfo (val filmInfo: FilmInfo) : AppState()
}