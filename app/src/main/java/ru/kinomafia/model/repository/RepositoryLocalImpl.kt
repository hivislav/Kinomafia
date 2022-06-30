package ru.kinomafia.model.repository

import ru.kinomafia.model.MyApplication
import ru.kinomafia.model.entities.FilmInfo
import ru.kinomafia.model.room.FavouriteFilmEntity

class RepositoryLocalImpl: RepositoryLocal {
    override fun getAllFavouriteFilms(): List<FilmInfo> {
        return convertFavouriteFilmEntityToFilmInfo(MyApplication.getFavouriteFilmDao().getAllFavouriteFilms())
    }

    override fun saveFavouriteFilm(filmInfo: FilmInfo) {
        MyApplication.getFavouriteFilmDao().insert(convertFilmInfoToFavouriteFilmEntity(filmInfo))
    }

    private fun convertFavouriteFilmEntityToFilmInfo(entityList: List<FavouriteFilmEntity>) : List<FilmInfo> {
        return entityList.map {
            FilmInfo(
                it.id, it.title, it.year, it.image, it.runtimeStr, it.plot, it.directors, it.stars, it.genres, it.note
            )
        }
    }

    private fun convertFilmInfoToFavouriteFilmEntity(filmInfo: FilmInfo) =
        FavouriteFilmEntity(
            filmInfo.id,
            filmInfo.title,
            filmInfo.year,
            filmInfo.image,
            filmInfo.runtimeStr ?: "",
            filmInfo.plot,
            filmInfo.directors,
            filmInfo.stars,
            filmInfo.genres,
            filmInfo.note
        )
}