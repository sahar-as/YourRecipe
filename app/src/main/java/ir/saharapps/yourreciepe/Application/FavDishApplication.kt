package ir.saharapps.yourreciepe.Application

import android.app.Application
import ir.saharapps.yourreciepe.model.database.FavDishDataBase
import ir.saharapps.yourreciepe.model.database.FavDishRepository

class FavDishApplication: Application() {
    private val database by lazy {FavDishDataBase.getDatabase(this@FavDishApplication)}
    val repository by lazy { FavDishRepository(database.favDishDao()) }
}