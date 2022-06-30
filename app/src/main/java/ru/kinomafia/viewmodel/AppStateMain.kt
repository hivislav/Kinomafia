package ru.kinomafia.viewmodel

import ru.kinomafia.model.entities.FilmItem

sealed class AppStateMain {
    data class SuccessLoadTop250(val filmItemListTop250: List<FilmItem>): AppStateMain()
    data class SuccessLoadMostPopular(val filmItemListMostPopular: List<FilmItem>): AppStateMain()
    data class Error(val error: Throwable): AppStateMain()
    object Loading: AppStateMain()
}