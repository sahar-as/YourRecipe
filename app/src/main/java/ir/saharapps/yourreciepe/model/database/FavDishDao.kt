package ir.saharapps.yourreciepe.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import ir.saharapps.yourreciepe.model.entities.FavDish
import kotlinx.coroutines.flow.Flow

@Dao
interface FavDishDao {
    @Insert
    suspend fun insertFaveDish(favDish: FavDish)

    @Query("SELECT * FROM fav_dish_table ORDER BY id")
    fun getAllDishes(): Flow<List<FavDish>>

    @Update
    suspend fun updateFavDishDetails(favDish: FavDish)

    @Query("SELECT * FROM fav_dish_table WHERE favorite_dish = 1")
    fun getFavoriteDishes(): Flow<List<FavDish>>
}