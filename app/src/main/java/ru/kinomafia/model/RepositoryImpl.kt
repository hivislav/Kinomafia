package ru.kinomafia.model

class RepositoryImpl: Repository {
    override fun getFilmInfoFromServer() = getDefaultFilm()
    override fun getFilmInfoFromLocalStorageNovelties() = getNoveltiesFilms()
    override fun getFilmInfoFromLocalStorageHits() = getHitsFilms()
    }