package com.tomg.popcorn.di

import android.content.Context
import com.tomg.popcorn.BaseApplication
import com.tomg.popcorn.repository.MovieRepository
import com.tomg.popcorn.api.MovieApi
import com.tomg.popcorn.db.FavouriteDao
import com.tomg.popcorn.db.GenreDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

 @Singleton
 @Provides
 fun application(@ApplicationContext app: Context): BaseApplication {
   return app as BaseApplication
 }

  @Singleton
  @Provides
  fun movieRepository(movieApi: MovieApi, favouriteDao: FavouriteDao, genreDao: GenreDao, @ApplicationContext context: Context): MovieRepository {
    return MovieRepository(movieApi, favouriteDao, genreDao, context)
  }
}