package com.tomg.popcorn.repository

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import com.tomg.popcorn.R
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
constructor(private val movieApi: MovieApi, private val favouriteDao: FavouriteDao, private val genreDao: GenreDao, private val context: Context) {

  fun favourites(): Flowable<List<Favourite>> = favouriteDao.listAll()

  fun loadMovieDetails(id: String): Flowable<Pair<Api.MovieDetails, List<Api.Movie>>> {
    return Flowable.combineLatest(movieApi.movieDetails(id).toFlowable(), movieApi.similarMovies(id).toFlowable(), { details, similar ->
      Pair(details.copy(backdrop = IMAGE_BASE_URL_WIDER + details.backdrop, poster = IMAGE_BASE_URL + details.poster), similar.results.map { it.copy(backdrop = IMAGE_BASE_URL + it.backdrop, poster = IMAGE_BASE_URL + it.poster) })
    })
  }

  fun loadMovies(): Flowable<Map<String, List<Api.Movie>>> {
    return Flowable.combineLatest(loadRandomGenreMovies().toFlowable(), loadPopularMovies().toFlowable(), loadRandomYearMovies().toFlowable(), { random, popular, randomYear ->
      mapOf(popular, random, randomYear)
    })
  }

  private fun loadRandomGenreMovies(): Single<Pair<String, List<Api.Movie>>> {
    return genreDao.loadRandomGenre()
      .flatMapSingle { genre ->
        movieApi.movies(listOf(genre.id.toString())).map {
          Pair(genre.name, it.results.map { it.copy(backdrop = IMAGE_BASE_URL + it.backdrop, poster = IMAGE_BASE_URL + it.poster) })
        }
    }
  }
  private fun loadRandomYearMovies(): Single<Pair<String, List<Api.Movie>>> {
    val randomYear = (1990..2021).random().toString()
    return movieApi.movies(year = randomYear).map {
      Pair("${context.getString(R.string.movies_released_in)} $randomYear", it.results.map { it.copy(backdrop = IMAGE_BASE_URL + it.backdrop, poster = IMAGE_BASE_URL + it.poster) })
    }
  }

  @SuppressLint("CheckResult")
  private fun loadPopularMovies(): Single<Pair<String, List<Api.Movie>>> {
    return movieApi.popularMovies().map { movies -> Pair(context.getString(R.string.popular_movies) ,movies.results.map { it.copy(backdrop = IMAGE_BASE_URL + it.backdrop, poster = IMAGE_BASE_URL + it.poster) }) }
  }

  fun searchMovies(title: String?): Flowable<List<Api.Movie>>{
    return movieApi.searchMovies(query = title).toFlowable().map { it.results.map { it.copy(backdrop = IMAGE_BASE_URL + it.backdrop, poster = IMAGE_BASE_URL + it.poster) } }
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
    private const val IMAGE_BASE_URL_WIDER = "https://image.tmdb.org/t/p/w500/"
  }

}