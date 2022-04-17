package ru.kinomafia.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import ru.kinomafia.R

@Parcelize
data class FilmInfo(
    val film: Film = getDefaultFilm(),
    val filmAnnotation: Int = R.string.terminator2,
    val director: String = "Джеймс Кэмерон",
    val actors: String = "Арнольд Шварценеггер, Линда Хэмилтон, Эдвард Ферлонг, Роберт Патрик"
    ) : Parcelable

fun getDefaultFilm() = Film(R.drawable.terminator2poster, "Терминатор 2: Судный день",
    "фантастика, боевик", 137, 1991)

fun getNoveltiesFilms(): List<FilmInfo> {
    return listOf(
        FilmInfo(Film(R.drawable.pahanhautoja, "Скрежет", "ужасы", 90, 2022)),
        FilmInfo(Film(R.drawable.the_exorcism_of_god,"Последнее пришествие дьявола", "ужасы, драма", 98, 2021)),
        FilmInfo(Film(R.drawable.uncharted, "Анчартед: На картах не значится", "боевик, приключения", 115, 2022)),
        FilmInfo(Film(R.drawable.everything_everywhere_all_at_once, "Всё везде и сразу","фантастика, фэнтези, боевик, комедия, приключения", 139, 2022)),
        FilmInfo(Film(R.drawable.finnik, "Финник", "мультфильм, приключения, детский", 85, 2022))
        )
}

fun getHitsFilms(): List<FilmInfo> {
    return listOf(
        FilmInfo(Film(R.drawable.terminator2poster, "Терминатор 2: Судный день","фантастика, боевик", 137, 1991)),
        FilmInfo(Film(R.drawable.lotr, "Властелин колец: Братство кольца", "фэнтази, приключения, драма", 178, 2001)),
        FilmInfo(Film(R.drawable.interstellar, "Интерстеллар","фантастика, драма, приключения", 169, 2014)),
        FilmInfo(Film(R.drawable.matrix, "Матрица","фантастика, боевик", 136, 1999)),
        FilmInfo(Film(R.drawable.spider_man_no_way_home, "Человек-паук: Нет пути домой","фантастика, боевик, приключения, фэнтези", 148, 2021)))
}





