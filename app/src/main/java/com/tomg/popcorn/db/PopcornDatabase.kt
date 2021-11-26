package com.tomg.popcorn.db

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [(Genre::class)], version = 1)
abstract class PopcornDatabase: RoomDatabase() {

  abstract fun genreDao() : GenreDao
  abstract fun favouriteDao(): FavouriteDao
}