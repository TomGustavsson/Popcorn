package com.tomg.popcorn.di

import android.content.Context
import androidx.room.Room
import com.tomg.popcorn.db.FavouriteDao
import com.tomg.popcorn.db.GenreDao
import com.tomg.popcorn.db.PopcornDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {


  @Singleton
  @Provides
  fun popcornDatabase(context: Context): PopcornDatabase {
    return Room.databaseBuilder(context, PopcornDatabase::class.java, "popcorn_database.db")
      .fallbackToDestructiveMigration()
      .build()
  }

  @Singleton
  @Provides
  fun storeDao(icaDatabase: PopcornDatabase): GenreDao {
    return icaDatabase.genreDao()
  }

  @Singleton
  @Provides
  fun articleDao(icaDatabase: PopcornDatabase): FavouriteDao{
    return icaDatabase.favouriteDao()
  }
}