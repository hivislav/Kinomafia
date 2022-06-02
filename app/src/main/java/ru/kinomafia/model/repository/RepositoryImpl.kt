package ru.kinomafia.model.repository

import ru.kinomafia.model.ListFilmLoader
import ru.kinomafia.model.MOST_POPULAR_MOVIES_KEY
import ru.kinomafia.model.TOP_250_MOVIES_KEY
import ru.kinomafia.model.entities.FilmItem


class RepositoryImpl: Repository {

    override fun getMostPopularMoviesFilmListFromServer(): List<FilmItem> {
        val result = emptyList<FilmItem>().toMutableList()
        val dto = ListFilmLoader.loadListFilm(MOST_POPULAR_MOVIES_KEY)
        dto?.let{
            for (i in dto.items) {
                val filmItem = FilmItem(
                    i.id.toString(),
                    i.title.toString(),
                    i.year.toString(),
                    i.image.toString(),
                    i.imDbRating.toString()
                )
                result.add(filmItem)
            }
        }
        return result
    }

    override fun getTop250FilmListFromServer(): List<FilmItem> {
        val result = emptyList<FilmItem>().toMutableList()
        val dto = ListFilmLoader.loadListFilm(TOP_250_MOVIES_KEY)
        dto?.let{
            for (i in dto.items) {
                val filmItem = FilmItem(
                    i.id.toString(),
                    i.title.toString(),
                    i.year.toString(),
                    i.image.toString(),
                    i.imDbRating.toString()
                )
                result.add(filmItem)
            }
        }
        return result
    }
}