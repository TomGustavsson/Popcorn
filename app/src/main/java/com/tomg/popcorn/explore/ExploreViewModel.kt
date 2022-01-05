package com.tomg.popcorn.explore

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
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel
@Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

  private val disposables: CompositeDisposable = CompositeDisposable()

  var movieList: MutableState<Map<String,List<Api.Movie>>> = mutableStateOf(mapOf())
  var searchedMovies: MutableState<List<Api.Movie>> = mutableStateOf(emptyList())

  val isLoading: MutableState<Pair<Throwable?,Boolean>> = mutableStateOf(Pair(null, true))

  val query = mutableStateOf("")

  init {
    loadPopularMovies()
  }

  fun favourites(): Flowable<List<Favourite>> {
    return movieRepository.favourites()
  }

  fun loadPopularMovies(){
    isLoading.value = Pair(null, true)
    disposables += movieRepository.loadMovies()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        movieList.value = it
        Timber.d("Popular movies successfully loaded.")
        isLoading.value = Pair(null, false)
      }, {
        Timber.e(it,"Error loading popular movies.")
        isLoading.value = Pair(it, false)
      })
  }

  fun saveMovie(movie: Api.Movie){
    disposables += movieRepository.saveMovie(movie.toFavourite())
      .subscribe({
        Timber.d("Movie successfully added to favourites.")
       }, {
        Timber.e(it,"Error saving movie to favourites.")
      })
  }

  fun deleteFavourite(movie: Api.Movie){
    disposables += movieRepository.deleteFavourite(movie.toFavourite())
      .subscribe({
        Timber.d("Favourite successfully deleted.")
      }, {
        Timber.e(it,"Error deleting favourite.")
      })
  }

  fun changeQuery(newQuery: String){
    query.value = newQuery
    isLoading.value = Pair(null, true)
    searchMovies()
  }

  private fun searchMovies(){
    disposables += movieRepository.searchMovies(query.value)
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .doFinally { isLoading.value = Pair(null, false) }
      .subscribe({
        searchedMovies.value = it
      }, {
        Timber.e(it,"Error fetching movies with query: ${query.value}")
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