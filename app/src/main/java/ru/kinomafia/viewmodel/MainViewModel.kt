package ru.kinomafia.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kinomafia.model.IMDB_MOST_POPULAR_MOVIES_END_POINT
import ru.kinomafia.model.IMDB_TOP_250_MOVIES_END_POINT
import ru.kinomafia.model.entities.FilmItem
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO
import ru.kinomafia.model.entities.rest_entities.FilmItemListDTO
import ru.kinomafia.model.repository.RepositoryRemote
import ru.kinomafia.model.repository.RepositoryRemoteImpl

class MainViewModel(
    private val liveData: MutableLiveData<AppStateMain> = MutableLiveData(),
    private val repositoryRemoteImpl: RepositoryRemoteImpl = RepositoryRemoteImpl()
) : ViewModel() {

    var listTop250 = listOf<FilmItem>()
    var listMostPopular = listOf<FilmItem>()

    fun getLiveData(): LiveData<AppStateMain>{
        return liveData
    }

    fun getDataFromServer() {
        liveData.postValue(AppStateMain.Loading)
        repositoryRemoteImpl.getTop250FilmListFromServer(callback)
        repositoryRemoteImpl.getMostPopularMoviesFilmListFromServer(callback)
    }

    private val callback = object: Callback<FilmItemListDTO> {
        override fun onResponse(call: Call<FilmItemListDTO>, response: Response<FilmItemListDTO>) {

            val typeOfResponse = response.raw().request().url().toString()

            if (response.isSuccessful) {
                response.body()?.let {
                    if (typeOfResponse.contains(IMDB_TOP_250_MOVIES_END_POINT)) {
                        liveData.postValue(AppStateMain.SuccessLoadTop250(repositoryRemoteImpl.converterDTOtoItemList(it)))
                    }
                    if (typeOfResponse.contains(IMDB_MOST_POPULAR_MOVIES_END_POINT)) {
                        liveData.postValue(AppStateMain.SuccessLoadMostPopular(repositoryRemoteImpl.converterDTOtoItemList(it)))
                    }
                }
            } else {
                val error = Throwable(response.code().toString())
                liveData.postValue(AppStateMain.Error(error))
            }
        }

        override fun onFailure(call: Call<FilmItemListDTO>, t: Throwable) {
            liveData.postValue(AppStateMain.Error(t))
        }
    }


    fun getFilmListsSortByRateFromServer() {
//        liveData.value = AppStateMain.Loading
//        viewModelScope.launch(Dispatchers.Main) {
//            val taskFilmLists = async(Dispatchers.IO) {repositoryRemoteImpl.getFilmListsByRateFromServer()}
//            val filmLists = taskFilmLists.await()
//            if (isActive) {
//                liveData.value = AppStateMain.Success(filmLists[0], filmLists[1])
//            }
//        }
    }
}