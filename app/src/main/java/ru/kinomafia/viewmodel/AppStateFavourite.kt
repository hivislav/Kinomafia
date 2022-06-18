package ru.kinomafia.viewmodel

import ru.kinomafia.model.entities.FilmInfo

sealed class AppStateFavourite {
    object Loading: AppStateFavourite()
    data class Success(val favoriteFilmList: List<FilmInfo>): AppStateFavourite()
    data class Error(val error: Throwable): AppStateFavourite()
}
