package ir.saharapps.yourreciepe.model.database

import android.content.Context
import androidx.room.Database
import androidx.room.Entity
import androidx.room.Room
import androidx.room.RoomDatabase
import ir.saharapps.yourreciepe.model.entities.FavDish


@Database(entities = [FavDish::class], version = 1)
abstract class FavDishDataBase: RoomDatabase(){

    abstract fun favDishDao(): FavDishDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: FavDishDataBase? = null

        fun getDatabase(context: Context): FavDishDataBase {
            // if the INSTANCE is not null, then return it,
            // if it is, then create the database
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    FavDishDataBase::class.java,
                    "fav_dish_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                // return instance
                instance
            }
        }
    }

}