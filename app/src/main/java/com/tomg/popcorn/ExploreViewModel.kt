package com.tomg.popcorn

import android.util.Log
import androidx.lifecycle.ViewModel
import com.tomg.popcorn.api.Api
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class ExploreViewModel
@Inject constructor(private val movieRepository: MovieRepository): ViewModel() {

  private val disposables: CompositeDisposable = CompositeDisposable()

  fun loadPopularMovies(){
    disposables += movieRepository.loadPopularMovies()
      .subscribeOn(Schedulers.io())
      .observeOn(AndroidSchedulers.mainThread())
      .subscribe({
        Log.d("TGIW", it.map { it.title }.toString())
      }, {
        Log.d("TGIW", it.toString())
        //Log.d("TGIW", "error")
      })
  }

  override fun onCleared() {
    super.onCleared()
    disposables.clear()
  }

  companion object {
    fun getMockData(): List<Api.Movie> {
      return listOf(
        Api.Movie("1234", "Shawshank redemption", "1994", "", listOf(24,5,26), "", "9.4", "200.004"),
        Api.Movie("12434", "Manhattan", "2021", "", listOf(24,5,26), "", "7.9", "280.004"),
        Api.Movie("1234", "Harry potter and the deadly hallows: part 1", "2010", "", listOf(24,5,26), "", "7.7", "205.004"),
        Api.Movie("1234", "Harry potter and the deadly hallows: part 1", "2011", "", listOf(24,5,26), "", "8.1", "208.004"),
        Api.Movie("1234", "The green mile", "1998", "", listOf(24,5,26), "", "8.4", "200.004"),
        Api.Movie("1234", "De Oost", "2021", "", listOf(24,5,26), "", "7.0", "230.004")
      )
    }
  }
}