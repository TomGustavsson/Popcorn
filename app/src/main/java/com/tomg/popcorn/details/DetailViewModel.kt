package com.tomg.popcorn.details

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
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class DetailViewModel
@Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

  private val disposables: CompositeDisposable = CompositeDisposable()

  val similarMovies: MutableState<List<Api.Movie>> = mutableStateOf(emptyList())
  val movieDetail: MutableState<Api.MovieDetails?> = mutableStateOf(null)

  val isLoading: MutableState<Pair<Throwable?,Boolean>> = mutableStateOf(Pair(null, true))
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
        Timber.d("Movie successfully loaded.")
        isLoading.value = Pair(null, false)
      }, {
        Timber.e(it,"Error loading movie.")
        isLoading.value = Pair(it, false)
      })
  }

  fun saveMovie(movie: Api.MovieDetails){
    disposables += movieRepository.saveMovie(movie.toFavourite())
      .subscribe({
        Timber.d("Movie successfully saved.")
      }, {
        Timber.e(it,"Error saving movie.")
      })
  }

  fun saveMovie(movie: Api.Movie){
    disposables += movieRepository.saveMovie(movie.toFavourite())
      .subscribe({
        Timber.d("Movie successfully saved.")
      }, {
        Timber.e(it,"Error saving movie.")
      })
  }

  fun deleteFavourite(movie: Api.MovieDetails){
    disposables += movieRepository.deleteFavourite(movie.toFavourite())
      .subscribe({
        Timber.d("Movie successfully deleted.")
      }, {
        Timber.e(it,"Error deleting movie.")
      })
  }

  fun deleteFavourite(movie: Api.Movie){
    disposables += movieRepository.deleteFavourite(movie.toFavourite())
      .subscribe({
        Timber.d("Movie successfully deleted.")
      }, {
        Timber.e(it,"Error deleting movie.")
      })
  }

  override fun onCleared() {
    super.onCleared()
    disposables.dispose()
  }
}