package ru.kinomafia.model.repository

import com.google.gson.GsonBuilder
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kinomafia.model.FilmApi
import ru.kinomafia.model.IMDB_API_URL
import ru.kinomafia.model.entities.FilmInfo
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO
import ru.kinomafia.model.entities.rest_entities.FilmItemListDTO


class RepositoryRemoteImpl: RepositoryRemote {

    private val retrofit = Retrofit.Builder()
        .baseUrl(IMDB_API_URL)
        .addConverterFactory(
            GsonConverterFactory.create(
                GsonBuilder().setLenient().create()
            )
        )
        .build().create(FilmApi::class.java)

    fun converterDTOtoItemList(filmItemListDTO: FilmItemListDTO): List<FilmItem> {
        val result = emptyList<FilmItem>().toMutableList()
        val dto = filmItemListDTO.items
        for (i in dto) {
            val filmItem = FilmItem(
                i.id.toString(),
                i.title.toString(),
                i.year.toString(),
                i.image.toString(),
                i.imDbRating.toString()
            )
            result.add(filmItem)
        }
        return result
    }

    fun converterDTOtoFilmInfo(filmInfoDTO: FilmInfoDTO): FilmInfo {
        return FilmInfo(
            filmInfoDTO.id,
            filmInfoDTO.title,
            filmInfoDTO.year,
            filmInfoDTO.image,
            filmInfoDTO.runtimeStr,
            filmInfoDTO.plot,
            filmInfoDTO.directors,
            filmInfoDTO.stars,
            filmInfoDTO.genres,
            ""
        )
    }

    override fun getFilmInfo(id: String, callback: Callback<FilmInfoDTO>) {
        retrofit.getFilmInfo(filmID = id).enqueue(callback)
    }

    override fun getTop250FilmListFromServer(callback: Callback<FilmItemListDTO>) {
        retrofit.getFilmItemListTop250().enqueue(callback)
    }

    override fun getMostPopularMoviesFilmListFromServer(callback: Callback<FilmItemListDTO>) {
        retrofit.getFilmItemListMostPopular().enqueue(callback)
    }


    override fun getFilmListsByRateFromServer(): List<List<FilmItem>> {
//        val listTop250 = getTop250FilmListFromServer().filter {
//           it.imDbRating != "" && it.imDbRating.toDouble() >= 6.0}
//        val listMostPopular = getMostPopularMoviesFilmListFromServer().filter {
//           it.imDbRating != "" && it.imDbRating.toDouble() >= 6.0}
//        return listOf(listTop250, listMostPopular)
        return emptyList()
    }
}