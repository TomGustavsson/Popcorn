package com.tomg.popcorn.details

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.tomg.popcorn.api.Api
import com.tomg.popcorn.db.Favourite
import com.tomg.popcorn.db.toFavourite
import com.tomg.popcorn.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

  private val disposables: CompositeDisposable = CompositeDisposable()

  val similarMovies: MutableState<List<Api.Movie>> = mutableStateOf(emptyList())
  val movieDetail: MutableState<Api.MovieDetails?> = mutableStateOf(null)

  fun favourites(): Flowable<List<Favourite>> {
    return movieRepository.favourites()
  }

  fun loadMovie(id: String){
    disposables += movieRepository.loadMovieDetails(id)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        similarMovies.value = it.second
        movieDetail.value = it.first
      }, {
        Log.d("TGIW", it.toString())
      })
  }

  fun saveMovie(movie: Api.MovieDetails){
    disposables += movieRepository.saveMovie(movie.toFavourite())
      .subscribe({
        Log.d("TGIW", "favourite was added..")
      }, {
        Log.d("TGIW", it.toString())
      })
  }

  fun saveMovie(movie: Api.Movie){
    disposables += movieRepository.saveMovie(movie.toFavourite())
      .subscribe({
        Log.d("TGIW", "favourite was added..")
      }, {
        Log.d("TGIW", it.toString())
      })
  }

  fun deleteFavourite(movie: Api.MovieDetails){
    disposables += movieRepository.deleteFavourite(movie.toFavourite())
      .subscribe({
        Log.d("TGIW", "favourite was deleted..")
      }, {
        Log.d("TGIW", it.toString())
      })
  }

  fun deleteFavourite(movie: Api.Movie){
    disposables += movieRepository.deleteFavourite(movie.toFavourite())
      .subscribe({
        Log.d("TGIW", "favourite was deleted..")
      }, {
        Log.d("TGIW", it.toString())
      })
  }

  override fun onCleared() {
    super.onCleared()
    disposables.dispose()
  }
}