package ru.kinomafia.viewmodel

import ru.kinomafia.model.FilmItem

sealed class AppState {
    data class Success(val filmDataNovelties: List<FilmItem>, val filmDataHits: List<FilmItem>): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}