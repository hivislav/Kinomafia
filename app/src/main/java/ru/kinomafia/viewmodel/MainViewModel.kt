package ru.kinomafia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kinomafia.model.repository.Repository
import ru.kinomafia.model.repository.RepositoryImpl

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()
) : ViewModel() {

    fun getLiveData(): LiveData<AppState>{
        return liveDataToObserve
    }

    fun getFilmInfo() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
                liveDataToObserve.postValue(
                    AppState.Success(
                        repositoryImpl.getMostPopularMoviesFilmListFromServer(),
                        repositoryImpl.getTop250FilmListFromServer()
                    )
                )
        }.start()
    }
}