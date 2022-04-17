package ru.kinomafia.viewmodel

import ru.kinomafia.model.FilmInfo

sealed class AppState {
    data class Success(val filmDataNovelties: List<FilmInfo>, val filmDataHits: List<FilmInfo>): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}