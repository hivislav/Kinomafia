package ru.kinomafia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kinomafia.model.Repository
import ru.kinomafia.model.RepositoryImpl
import java.lang.Exception
import java.lang.Thread.sleep

class MainViewModel(
    private val liveDataToObserve: MutableLiveData<AppState> = MutableLiveData(),
    private val repositoryImpl: Repository = RepositoryImpl()) : ViewModel() {

    fun getLiveData(): LiveData<AppState>{
        return liveDataToObserve
    }

    fun getFilmInfo() = getDataFromLocalSource()

    private fun getDataFromLocalSource() {
        liveDataToObserve.value = AppState.Loading
        Thread {
            sleep(2000)
            val random = (0..3).random()
            if (random < 2) {
                liveDataToObserve.postValue(AppState.Error(IllegalStateException("")))
            } else {
                liveDataToObserve.postValue(
                    AppState.Success(
                        repositoryImpl.getFilmInfoFromLocalStorageNovelties(),
                        repositoryImpl.getFilmInfoFromLocalStorageHits()
                    )
                )
            }
        }.start()
    }
}