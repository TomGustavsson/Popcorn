package com.tomg.popcorn.di

import android.content.Context
import com.squareup.moshi.Moshi
import com.tomg.popcorn.BaseApplication
import com.tomg.popcorn.MovieRepository
import com.tomg.popcorn.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
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
  fun movieRepository(movieApi: MovieApi): MovieRepository {
    return MovieRepository(movieApi)
  }
}