package com.tomg.popcorn.api

import com.squareup.moshi.Json
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MovieApi {

  /** Could change page for more variation later **/
  @GET("discover/movie?&discover/movie?sort_by=popularity.desc&page=1")
  fun popularMovies(): Single<Api.Movies>

  /** Use this endpoint later for movie detal view **/
  @GET("movie/{id}/similar?&language=en-US&page=1")
  fun similarMovies(@Path("id") id: String): Single<Api.Movies>
}

object Api {

  data class Movies(@Json(name = "results") val results: List<Movie>)

  data class Movie(
    @Json(name = "id") val id: String,
    @Json(name = "original_title") val title: String,
    @field:Json(name="backdrop_path")
    val backdrop: String,
    @field:Json(name="genre_ids")
    val genres: List<Int>,
    @field:Json(name="poster_path")
    val poster: String,
    @field:Json(name="vote_average")
    val rating: String,
    @field:Json(name="vote_count")
    val votes: String,
    @field:Json(name="release_date")
    val releaseDate: String,
    @field:Json(name="overview")
    val overview: String
    )
}