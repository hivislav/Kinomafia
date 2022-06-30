package ru.kinomafia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kinomafia.model.entities.FilmInfo
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO
import ru.kinomafia.model.repository.RepositoryLocalImpl
import ru.kinomafia.model.repository.RepositoryRemoteImpl

class FilmInfoViewModel(private val liveData: MutableLiveData<AppStateFilmInfo> =  MutableLiveData(),
                        private val repositoryRemoteImpl: RepositoryRemoteImpl = RepositoryRemoteImpl(),
                        private val repositoryLocalImpl: RepositoryLocalImpl = RepositoryLocalImpl()
) : ViewModel() {

    fun getLiveData(): MutableLiveData<AppStateFilmInfo> {
        return liveData
    }

    fun addFavouriteFilm(filmInfo: FilmInfo) {
        viewModelScope.launch(Dispatchers.IO) {
            repositoryLocalImpl.saveFavouriteFilm(filmInfo)
        }
    }

    fun loadFilmInfoFromServer(id: String) {
        liveData.postValue(AppStateFilmInfo.Loading)
        repositoryRemoteImpl.getFilmInfo(id, callback)
    }


    private val callback = object: Callback<FilmInfoDTO> {
        var id = ""

        override fun onResponse(call: Call<FilmInfoDTO>, response: Response<FilmInfoDTO>) {
            response.body()?.let {
                id = it.id
            }

            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(AppStateFilmInfo.SuccessLoadingFilmInfo(repositoryRemoteImpl.converterDTOtoFilmInfo(it)))
                }
            } else {
                val error = Throwable(response.code().toString())
                liveData.postValue(AppStateFilmInfo.Error(id, error))
            }
        }

        override fun onFailure(call: Call<FilmInfoDTO>, t: Throwable) {
            liveData.postValue(AppStateFilmInfo.Error(id, t))
        }
    }
}