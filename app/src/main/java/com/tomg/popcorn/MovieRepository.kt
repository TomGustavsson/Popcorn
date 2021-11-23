package com.tomg.popcorn

import com.tomg.popcorn.api.Api
import com.tomg.popcorn.api.MovieApi
import io.reactivex.Single
import javax.inject.Inject

class MovieRepository
@Inject
constructor(private val movieApi: MovieApi) {

  fun loadPopularMovies(): Single<List<Api.Movie>> {
    return movieApi.popularMovies().map { it.results }
  }
}