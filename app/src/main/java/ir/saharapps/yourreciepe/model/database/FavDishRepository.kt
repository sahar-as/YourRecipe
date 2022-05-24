package ir.saharapps.yourreciepe.model.database

import androidx.annotation.WorkerThread
import ir.saharapps.yourreciepe.model.entities.FavDish

class FavDishRepository(private val favDishDao: FavDishDao) {

    @WorkerThread
    suspend fun insertFaveDishData(favDish: FavDish){
        favDishDao.insertFaveDish(favDish)
    }
}