package ru.kinomafia.view.film_info

import android.app.IntentService
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.gson.Gson
import ru.kinomafia.model.API_KEY
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO
import ru.kinomafia.view.film_info.FilmInfoFragment.Companion.BUNDLE_KEY_FILM_INFO
import ru.kinomafia.view.film_info.FilmInfoFragment.Companion.FILM_ID_EXTRA
import ru.kinomafia.view.film_info.FilmInfoFragment.Companion.FILM_INFO_INTENT_FILTER
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.stream.Collectors
import javax.net.ssl.HttpsURLConnection


class FilmInfoService(name : String = "") : IntentService(name) {
    override fun onHandleIntent(intent: Intent?) {
        intent?.let {
            val id = it.getStringExtra(FILM_ID_EXTRA)
            loadFilmInfo(id.toString())
        }
    }

    private fun loadFilmInfo(id: String) {
        val uri = URL("https://imdb-api.com/en/API/Title/$API_KEY/$id")
        //создаем соединение
        lateinit var urlConnection: HttpsURLConnection

        Thread {
            //открываем соединение
            urlConnection = uri.openConnection() as HttpsURLConnection
            //создаем запрос на сервер
            urlConnection.requestMethod = "GET"
            urlConnection.readTimeout = 10000
            //считываем данные из входящего потока
            val bufferedReader = BufferedReader(InputStreamReader(urlConnection.inputStream))
            //преобразовываем ответ от сервера (JSON) в модель данных (FilmInfoDTO)
            val lines = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
                getLinesForOld(bufferedReader)
            } else {
                getLines(bufferedReader)
            }

            val filmInfoDTO = Gson().fromJson(lines, FilmInfoDTO::class.java)
            val intent = Intent(FILM_INFO_INTENT_FILTER).apply {
                putExtra(BUNDLE_KEY_FILM_INFO, filmInfoDTO)
            }

            LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
            urlConnection.disconnect()
        }.start()
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