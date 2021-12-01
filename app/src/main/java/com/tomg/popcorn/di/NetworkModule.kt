package com.tomg.popcorn.di

import com.squareup.moshi.Moshi
import com.tomg.popcorn.api.MovieApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

  private const val BASE_URL = "https://api.themoviedb.org/3/"
  private const val BEARER_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiI4ZWM2ZmM1ZDMxZGEyZWFjYTA0ODEwYzkxZjMxOTlkNSIsInN1YiI6IjYxOTc2MmZkYWY1OGNiMDA5MWIyYjk4ZiIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.FuTnThwui8bvVOXc_8lrwqlFDmj1G_g57vrBkvkB9X0"

  @Singleton
  @Provides
  fun moshi(): Moshi {
    return Moshi.Builder()
      .build()
  }

  @Singleton
  @Provides
  fun httpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()

    return builder
      //.cache(httpCache) // Make it cache later..
      .addInterceptor { chain ->
        val newRequest = chain.request().newBuilder()
          .addHeader("Authorization", "Bearer " + BEARER_TOKEN)
          .build()
        chain.proceed(newRequest)
      }
      .retryOnConnectionFailure(true)
      .connectTimeout(30, TimeUnit.SECONDS)
      .writeTimeout(30, TimeUnit.SECONDS)
      .readTimeout(30, TimeUnit.SECONDS)
      .build()
  }
  @Singleton
  @Provides
  fun retrofit(httpClient: OkHttpClient, moshi: Moshi): Retrofit {
    return Retrofit.Builder()
      .baseUrl(BASE_URL)
      .client(httpClient)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .addConverterFactory(ScalarsConverterFactory.create())
      .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
      .build()
  }

  @Singleton
  @Provides
  fun movieApi(retrofit: Retrofit): MovieApi {
    return retrofit.create(MovieApi::class.java)
  }
}