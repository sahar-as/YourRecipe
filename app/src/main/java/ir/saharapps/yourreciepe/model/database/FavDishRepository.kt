package ir.saharapps.yourreciepe.model.database

import androidx.annotation.WorkerThread
import ir.saharapps.yourreciepe.model.entities.FavDish
import kotlinx.coroutines.flow.Flow

class FavDishRepository(private val favDishDao: FavDishDao) {

    @WorkerThread
    suspend fun insertFaveDishData(favDish: FavDish){
        favDishDao.insertFaveDish(favDish)
    }

    val allDishesList: Flow<List<FavDish>> = favDishDao.getAllDishes()

    @WorkerThread
    suspend fun updateFaveDishDetailsData(favDish: FavDish){
        favDishDao.updateFavDishDetails(favDish)
    }

    val favoriteDishes: Flow<List<FavDish>> = favDishDao.getFavoriteDishes()

    @WorkerThread
    suspend fun deleteSelectedDish(favDish: FavDish){
        favDishDao.deleteSelectedDish(favDish)
    }

    fun filterDishes(filterItem: String): Flow<List<FavDish>> =
        favDishDao.getFilterDishes(filterItem)


    fun getRepeatedItem(itemTitle: String): Flow<List<FavDish>> =
        favDishDao.getRepeatedDish(itemTitle)
}