package com.tomg.popcorn.repository

import com.tomg.popcorn.api.Api
import com.tomg.popcorn.api.MovieApi
import com.tomg.popcorn.db.Favourite
import com.tomg.popcorn.db.FavouriteDao
import com.tomg.popcorn.db.GenreDao
import com.tomg.popcorn.db.toFavourite
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MovieRepository
@Inject
constructor(private val movieApi: MovieApi, private val favouriteDao: FavouriteDao, private val genreDao: GenreDao) {

  fun favourites(): Flowable<List<Favourite>> = favouriteDao.listAll()

  fun loadPopularMovies(): Single<List<Api.Movie>> {
    return movieApi.popularMovies().map { movies -> movies.results.map { it.copy(backdrop = IMAGE_BASE_URL + it.backdrop, poster = IMAGE_BASE_URL + it.poster) } }
  }

  fun saveMovie(movie: Api.Movie): Completable {
    return Completable.fromCallable {
      favouriteDao.saveFavourite(movie.toFavourite())
    }.subscribeOn(Schedulers.io())
  }

  fun deleteFavourite(favourite: Favourite): Completable {
    return Completable.fromCallable {
      favouriteDao.deleteFavourite(favourite)
    }.subscribeOn(Schedulers.io())
  }

  companion object {
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200/"
  }

}