package com.tomg.popcorn

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import com.facebook.stetho.Stetho
import com.tomg.popcorn.api.MovieApi
import com.tomg.popcorn.db.GenreDao
import com.tomg.popcorn.db.toDbModel
import dagger.hilt.android.HiltAndroidApp
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltAndroidApp
class BaseApplication : Application(){

  /** If the app is gonna need more sync work then we change these to work WorkManager **/
  @Inject
  lateinit var movieApi: MovieApi

  @Inject
  lateinit var genreDao: GenreDao

  override fun onCreate() {
    super.onCreate()
    Stetho.initializeWithDefaults(this)
    syncGenres()
  }

  @SuppressLint("CheckResult")
  private fun syncGenres(){
    movieApi.getGenreList()
      .subscribeOn(Schedulers.io())
      .subscribe({
        genreDao.insertAll(it.genres.map { it.toDbModel() })
      }, {
        Log.d("TGIW", it.toString())
      })
  }



}