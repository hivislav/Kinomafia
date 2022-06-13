package ru.kinomafia.model.room

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavouriteFilmEntity::class], version = 1, exportSchema = false)
abstract class FavouriteFilmDatabase: RoomDatabase() {
    abstract fun favouriteFilmDao(): FavouriteFilmDao
}