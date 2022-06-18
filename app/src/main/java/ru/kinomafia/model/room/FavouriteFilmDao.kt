package ru.kinomafia.model.room

import androidx.room.*

@Dao
interface FavouriteFilmDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(entity: FavouriteFilmEntity)

    @Delete
    fun delete(entity: FavouriteFilmEntity)

    @Update
    fun update(entity: FavouriteFilmEntity)

    @Query("SELECT * FROM favourite_film_entity")
    fun getAllFavouriteFilms(): List<FavouriteFilmEntity>
}