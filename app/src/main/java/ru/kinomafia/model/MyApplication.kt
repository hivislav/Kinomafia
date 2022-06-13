package ru.kinomafia.model

import android.app.Application
import androidx.room.Room
import ru.kinomafia.model.room.FavouriteFilmDao
import ru.kinomafia.model.room.FavouriteFilmDatabase
import java.util.*

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        appInstance = this
    }

    companion object {
        private var appInstance: MyApplication? = null
        private const val DB_NAME = "Favourite.db"
        private var database: FavouriteFilmDatabase? = null

        fun getFavouriteFilmDao(): FavouriteFilmDao {
            if (database == null) {
                if (appInstance == null) { throw IllformedLocaleException("")
                } else {
                    database = Room.databaseBuilder(appInstance!!.applicationContext, FavouriteFilmDatabase::class.java, DB_NAME)
                        .build()
                }
            }
            return database!!.favouriteFilmDao()
        }
    }
}