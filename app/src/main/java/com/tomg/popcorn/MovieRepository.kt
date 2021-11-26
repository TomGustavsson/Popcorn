package com.tomg.popcorn

import com.tomg.popcorn.api.Api
import com.tomg.popcorn.api.MovieApi
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository
@Inject
constructor(private val movieApi: MovieApi) {

  fun loadPopularMovies(): Single<List<Api.Movie>> {
    //return movieApi.popularMovies().map { it.results }
    return movieApi.popularMovies().map { movies -> movies.results.map { it.copy(backdrop = IMAGE_BASE_URL + it.backdrop, poster = IMAGE_BASE_URL + it.poster) } }
  }

  companion object {
    private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/w200/"
  }

}