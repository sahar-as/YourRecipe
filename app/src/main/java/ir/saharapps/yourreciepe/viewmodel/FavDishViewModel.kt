package ir.saharapps.yourreciepe.viewmodel

import androidx.lifecycle.*
import ir.saharapps.yourreciepe.model.database.FavDishRepository
import ir.saharapps.yourreciepe.model.entities.FavDish
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class FavDishViewModel(private val repository: FavDishRepository): ViewModel() {

    fun insert(dish: FavDish) = viewModelScope.launch {
        repository.insertFaveDishData(dish)
    }

    val allDishesList: LiveData<List<FavDish>> = repository.allDishesList.asLiveData()

    fun updateFavDishDetail(dish: FavDish) = viewModelScope.launch {
        repository.updateFaveDishDetailsData(dish)
    }

    val favoriteDishList: LiveData<List<FavDish>> = repository.favoriteDishes.asLiveData()
}

class FavDishViewModelFactory(private val repository: FavDishRepository): ViewModelProvider.Factory{
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(FavDishViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return FavDishViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown view model class!")
    }
}