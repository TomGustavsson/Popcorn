package com.tomg.popcorn.favourites

import androidx.lifecycle.ViewModel
import com.tomg.popcorn.db.Favourite
import com.tomg.popcorn.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavouriteViewModel
@Inject constructor(private val movieRepository: MovieRepository): ViewModel(){

  private val disposables: CompositeDisposable = CompositeDisposable()

  fun favourites(): Flowable<List<Favourite>> {
    return movieRepository.favourites()
  }
  fun delete(favourite: Favourite){
    disposables += movieRepository.deleteFavourite(favourite)
      .subscribe({
        Timber.d("Favourite was successfully deleted")
      }, {
        Timber.e(it,"Favourite was successfully deleted")
      })
  }
}