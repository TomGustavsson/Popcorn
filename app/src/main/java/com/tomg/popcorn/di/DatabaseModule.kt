package com.tomg.popcorn.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import com.tomg.popcorn.db.Converters
import com.tomg.popcorn.db.FavouriteDao
import com.tomg.popcorn.db.GenreDao
import com.tomg.popcorn.db.PopcornDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

  @Singleton
  @Provides
  fun popcornDatabase(@ApplicationContext context: Context, moshi: Moshi): PopcornDatabase {
    return Room.databaseBuilder(context, PopcornDatabase::class.java, "popcorn_database.db")
      .addTypeConverter(Converters(moshi))
      .fallbackToDestructiveMigration()
      .build()
  }

  @Singleton
  @Provides
  fun genreDao(popDatabase: PopcornDatabase): GenreDao {
    return popDatabase.genreDao()
  }

  @Singleton
  @Provides
  fun favouriteDao(icaDatabase: PopcornDatabase): FavouriteDao {
    return icaDatabase.favouriteDao()
  }

  @Singleton
  @Provides
  fun converters(moshi: Moshi): Converters {
    return Converters(moshi)
  }
}