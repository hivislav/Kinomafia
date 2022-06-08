package ru.kinomafia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

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
            val taskGetTop250 = async(Dispatchers.IO) { repositoryImpl.getTop250FilmListFromServer()}
            val taskGetMostPopular = async(Dispatchers.IO) { repositoryImpl.getMostPopularMoviesFilmListFromServer()}
            val listFilmItemTop250 = taskGetTop250.await()
            val listFilmItemMostPopular = taskGetMostPopular.await()
            if (isActive) {
                liveData.value = AppState.Success(listFilmItemTop250, listFilmItemMostPopular)
            }
        }
    }
}