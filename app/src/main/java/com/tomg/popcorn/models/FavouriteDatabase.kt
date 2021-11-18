package com.tomg.popcorn.models

data class Favourite(
  val title: String,
  val year: String,
  val released: String,
  val genre: String,
  val poster: String,
  val imdbRating: String,
  val imdbVotes: String,
  val usersRating: Int
){

}