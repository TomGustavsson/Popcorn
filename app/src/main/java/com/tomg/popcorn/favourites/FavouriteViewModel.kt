package com.tomg.popcorn.favourites

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.tomg.popcorn.api.Api
import com.tomg.popcorn.db.Favourite
import com.tomg.popcorn.db.toFavourite
import com.tomg.popcorn.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
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
        Log.d(TAG, "favourite was deleted..")
      }, {
        Log.d(TAG, "Error deleting : $it")
      })
  }

  companion object {
    const val TAG = "FavouriteViewModel"
  }
}