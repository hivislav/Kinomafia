package ru.kinomafia.model

class RepositoryImpl: Repository {
    override fun getFilmInfoFromServer(): FilmInfo {
        return FilmInfo()
    }

    override fun getFilmInfoFromLocalStorageNovelties(): List<FilmInfo> {
        return getNoveltiesFilms()
    }

    override fun getFilmInfoFromLocalStorageHits(): List<FilmInfo> {
        return getHitsFilms()
    }
}