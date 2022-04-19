package ru.kinomafia.model

import ru.kinomafia.R


data class FilmInfo(
    val film: Film = getDefaultFilm(),
    val filmAnnotation: Int = R.string.terminator2,
    val director: String = "Джеймс Кэмерон",
    val actors: String = "Арнольд Шварценеггер, Линда Хэмилтон, Эдвард Ферлонг, Роберт Патрик"
    )

fun getDefaultFilm() = Film(R.drawable.terminator2poster, "Терминатор 2: Судный день",
    "фантастика, боевик", 137, 1991)



