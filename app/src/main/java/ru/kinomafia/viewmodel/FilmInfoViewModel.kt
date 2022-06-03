package ru.kinomafia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.kinomafia.model.repository.RepositoryImpl

class FilmInfoViewModel(private val liveData: MutableLiveData<AppState> =  MutableLiveData(),
                        private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getLiveData(): MutableLiveData<AppState> {
        return liveData
    }

    fun loadFilmInfoFromServer(id: String) {
        liveData.postValue(AppState.Loading)
        Thread {
            if (repositoryImpl.getFilmInfo(id).title != "null") {
                liveData.postValue(AppState.SuccessLoadingFilmInfo(repositoryImpl.getFilmInfo(id)))
            } else {
                liveData.postValue(AppState.Error(id))
            }
        }.start()
    }
}