package ru.kinomafia.model

class RepositoryImpl: Repository {
    override fun getFilmInfoFromServer() = FilmInfo()
    override fun getFilmInfoFromLocalStorageNovelties() = getNoveltiesFilms()
    override fun getFilmInfoFromLocalStorageHits() = getHitsFilms()
    }