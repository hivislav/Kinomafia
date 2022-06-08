package ru.kinomafia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.kinomafia.model.entities.rest_entities.FilmInfoDTO
import ru.kinomafia.model.repository.RepositoryImpl

class FilmInfoViewModel(private val liveData: MutableLiveData<AppState> =  MutableLiveData(),
                        private val repositoryImpl: RepositoryImpl = RepositoryImpl()
) : ViewModel() {

    fun getLiveData(): MutableLiveData<AppState> {
        return liveData
    }

    fun loadFilmInfoFromServer(id: String) {
        liveData.postValue(AppState.Loading)
        repositoryImpl.getFilmInfo(id, callback)
    }

    private val callback = object: Callback<FilmInfoDTO> {
        override fun onResponse(call: Call<FilmInfoDTO>, response: Response<FilmInfoDTO>) {
            if (response.isSuccessful) {
                response.body()?.let {
                    liveData.postValue(AppState.SuccessLoadingFilmInfo(it))
                }
            } else {
                //TODO
            }
        }

        override fun onFailure(call: Call<FilmInfoDTO>, t: Throwable) {
            TODO("Not yet implemented")
        }

    }
}