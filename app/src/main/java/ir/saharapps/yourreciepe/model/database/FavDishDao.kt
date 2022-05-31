package ir.saharapps.yourreciepe.model.database

import androidx.room.*
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

    @Delete
    suspend fun deleteSelectedDish(favDish: FavDish)

    @Query("SELECT * FROM fav_dish_table WHERE type = :filterType")
    fun getFilterDishes(filterType: String): Flow<List<FavDish>>

    @Query("SELECT * FROM fav_dish_table WHERE title = :dishName")
    fun getRepeatedDish(dishName: String): Flow<List<FavDish>>
}