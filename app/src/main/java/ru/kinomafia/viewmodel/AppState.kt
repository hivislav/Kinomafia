package ru.kinomafia.viewmodel

import ru.kinomafia.model.FilmInfo

sealed class AppState {
    data class Success(val filmData: FilmInfo): AppState()
    data class Error(val error: Throwable): AppState()
    object Loading: AppState()
}