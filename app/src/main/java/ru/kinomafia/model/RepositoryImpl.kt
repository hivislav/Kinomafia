package ru.kinomafia.model

class RepositoryImpl: Repository {
    override fun getFilmInfoFromServer(): FilmInfo {
        return FilmInfo()
    }

    override fun getFilmInfoFromLocalStorage(): FilmInfo {
        return FilmInfo()
    }
}