package ru.kinomafia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import ru.kinomafia.model.repository.RepositoryRemote
import ru.kinomafia.model.repository.RepositoryRemoteImpl

class MainViewModel(
    private val liveData: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryRemoteImpl: RepositoryRemote = RepositoryRemoteImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState>{
        return liveData
    }

    fun getDataFromServer() {
        liveData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val taskTop250 = async(Dispatchers.IO) { repositoryRemoteImpl.getTop250FilmListFromServer() }
            val listFilmItemTop250 = taskTop250.await()
            val taskMostPopular = async ( Dispatchers.IO ){repositoryRemoteImpl.getMostPopularMoviesFilmListFromServer() }
            val listFilmItemMostPopular = taskMostPopular.await()
            if (isActive) {
                liveData.value = AppState.Success(listFilmItemTop250, listFilmItemMostPopular)
            }
        }
    }

    fun getFilmListsSortByRateFromServer() {
        liveData.value = AppState.Loading
        viewModelScope.launch(Dispatchers.Main) {
            val taskFilmLists = async(Dispatchers.IO) {repositoryRemoteImpl.getFilmListsByRateFromServer()}
            val filmLists = taskFilmLists.await()
            if (isActive) {
                liveData.value = AppState.Success(filmLists[0], filmLists[1])
            }
        }
    }
}