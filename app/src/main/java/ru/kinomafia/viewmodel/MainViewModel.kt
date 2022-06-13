package ru.kinomafia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.*

import ru.kinomafia.model.repository.Repository
import ru.kinomafia.model.repository.RepositoryImpl

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState>{
        return liveData
    }

    fun getDataFromServer() {
        liveData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val taskTop250 = async(Dispatchers.IO) { repositoryImpl.getTop250FilmListFromServer() }
            val listFilmItemTop250 = taskTop250.await()
            val taskMostPopular = async ( Dispatchers.IO ){repositoryImpl.getMostPopularMoviesFilmListFromServer() }
            val listFilmItemMostPopular = taskMostPopular.await()
            if (isActive) {
                liveData.value = AppState.Success(listFilmItemTop250, listFilmItemMostPopular)
            }
        }
    }

    fun getFilmListsSortByRateFromServer() {
        liveData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val taskFilmLists = async(Dispatchers.IO) {repositoryImpl.getFilmListsByRateFromServer()}
            val filmLists = taskFilmLists.await()
            if (isActive) {
                liveData.value = AppState.Success(filmLists[0], filmLists[1])
            }
        }
    }
}