package ru.kinomafia.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.kinomafia.model.repository.RepositoryLocalImpl

class FavouriteViewModel(private val liveData: MutableLiveData<AppStateFavourite> = MutableLiveData(),
                         private val repositoryLocalImpl: RepositoryLocalImpl = RepositoryLocalImpl()
): ViewModel() {

    fun getLiveData(): MutableLiveData<AppStateFavourite> {
        return liveData
    }

    fun getAllFavouriteFilms() {
        liveData.value = AppStateFavourite.Loading
        viewModelScope.launch(Dispatchers.IO) {
            liveData.postValue(AppStateFavourite.Success(repositoryLocalImpl.getAllFavouriteFilms()))
        }
    }

}