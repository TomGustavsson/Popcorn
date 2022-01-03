package com.tomg.popcorn.explore

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
class ExploreViewModel
@Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

  private val disposables: CompositeDisposable = CompositeDisposable()

  var movieList: MutableState<Map<String,List<Api.Movie>>> = mutableStateOf(mapOf())
  var searchedMovies: MutableState<List<Api.Movie>> = mutableStateOf(emptyList())

  val query = mutableStateOf("")

  init {
    loadPopularMovies()
  }

  fun favourites(): Flowable<List<Favourite>> {
    return movieRepository.favourites()
  }

  private fun loadPopularMovies(){
    disposables += movieRepository.loadMovies()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        movieList.value = it
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

  fun deleteFavourite(movie: Api.Movie){
    disposables += movieRepository.deleteFavourite(movie.toFavourite())
      .subscribe({
        Log.d("TGIW", "favourite was deleted..")
      }, {
        Log.d("TGIW", it.toString())
      })
  }

  fun changeQuery(newQuery: String){
    query.value = newQuery
    searchMovies()
  }

  fun searchMovies(){
    disposables += movieRepository.searchMovies(query.value)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        searchedMovies.value = it
        Log.d("TGIW","you searched " + it.toString())
      }, {
        Log.d("TGIW", it.toString())
      })
  }

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }

  companion object {
    fun getMockData(): List<Api.Movie> {
      return listOf(
        Api.Movie("1234", "Shawshank redemption", "", listOf(24,5,26),"", "9.4", "200.004", "", ""),
        Api.Movie("12434", "Manhattan", "", listOf(24,5,26), "", "7.9", "280.004","",""),
        Api.Movie("12434", "Manhattan", "", listOf(24,5,26), "", "7.9", "280.004","",""),
        Api.Movie("12434", "Manhattan", "", listOf(24,5,26), "", "7.9", "280.004","",""),
        Api.Movie("12434", "Manhattan", "", listOf(24,5,26), "", "7.9", "280.004","",""),
        Api.Movie("12434", "Manhattan", "", listOf(24,5,26), "", "7.9", "280.004","","")
      )
    }
  }
}