package ir.saharapps.yourreciepe.model.database

import androidx.room.Dao
import androidx.room.Insert
import ir.saharapps.yourreciepe.model.entities.FavDish

@Dao
interface FavDishDao {
    @Insert
    suspend fun insertFaveDish(favDish: FavDish)
}