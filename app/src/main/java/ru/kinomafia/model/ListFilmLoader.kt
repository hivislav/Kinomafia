package ru.kinomafia.model

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.google.gson.Gson
import ru.kinomafia.model.entities.rest_entities.FilmItemListDTO
import ru.kinomafia.viewmodel.AppState
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection

const val API_KEY = "k_tbc3c4u4"
const val TOP_250_MOVIES_KEY = "Top250Movies"
const val MOST_POPULAR_MOVIES_KEY = "MostPopularMovies"

object ListFilmLoader {
    //функция для загрузки данных
    fun loadListFilm(keyTypeToDownloadListFilm: String): FilmItemListDTO? {
        val uri = URL("https://imdb-api.com/API/$keyTypeToDownloadListFilm/$API_KEY")
        //создаем соединение
        lateinit var urlConnection: HttpsURLConnection

        return try {
            //открываем соединение
            urlConnection = uri.openConnection() as HttpsURLConnection
            //создаем запрос на сервер
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 10000
            //считываем данные из входящего потока
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            //преобразовываем ответ от сервера (JSON) в модель данных (FilmItemListDTO)
            val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                getLinesForOld(bufferedReader)
            } else {
                getLines(bufferedReader)
            }

            Gson().fromJson(lines, FilmItemListDTO::class.java)
        } catch (e: Exception) {
            e.printStackTrace()
            Log.d("myLogs", e.toString())
            null
        } finally {
            urlConnection.disconnect()
        }
    }

    //метод для старых версий
    private fun getLinesForOld(bufferedReader: BufferedReader): String {
        val rawData = StringBuilder(1024)
        var tempVariable: String?

        while (bufferedReader.readLine().also { tempVariable = it } != null) {
            rawData.append(tempVariable).append("\n")
        }
        bufferedReader.close()
        return rawData.toString()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun getLines(bufferedReader: BufferedReader): String {
        //читаем строки и ставим разделитель
        return bufferedReader.lines().collect(Collectors.joining("\n"))
    }
}