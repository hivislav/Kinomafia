package ru.kinomafia.viewmodel

import ru.kinomafia.model.entities.FilmInfo
import ru.kinomafia.model.entities.FilmItem

sealed class AppStateFilmInfo {
    data class Error(val filmID: String, val error: Throwable): AppStateFilmInfo()
    object Loading: AppStateFilmInfo()
    data class SuccessLoadingFilmInfo (val filmInfo: FilmInfo) : AppStateFilmInfo()
}