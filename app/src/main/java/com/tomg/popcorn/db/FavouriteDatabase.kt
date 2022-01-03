package com.tomg.popcorn.db

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.tomg.popcorn.api.Api
import io.reactivex.Flowable
import io.reactivex.Single

@Entity(tableName = "favourites")
data class Favourite(@PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int,
                     @ColumnInfo(name = "title") val title: String,
                     @ColumnInfo(name = "backdrop") val backDrop: String,
                     @ColumnInfo(name = "genres") val genres: List<Int>,
                     @ColumnInfo(name = "poster") val poster: String,
                     @ColumnInfo(name = "rating") val rating: String,
                     @ColumnInfo(name = "votes") val votes: String,
                     @ColumnInfo(name = "releaseDate") val releaseDate: String,
                     @ColumnInfo(name = "overview") val overview: String
                     )

@Dao
interface FavouriteDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun saveFavourite(favourite: Favourite)

  @Delete
  fun deleteFavourite(favourite: Favourite)

  @Query("SELECT * FROM favourites")
  fun listAll(): Flowable<List<Favourite>>

  @Query("SELECT * FROM favourites WHERE id=:id")
  fun loadFavourite(id: Int): Single<Favourite>
}

fun Api.Movie.toFavourite() = Favourite(
  id = id.toInt(),
  title = title,
  backDrop = backdrop,
  genres = genres,
  poster = poster,
  rating = rating,
  votes = votes,
  releaseDate = releaseDate,
  overview = overview
)

fun Api.MovieDetails.toFavourite() = Favourite(
  id = id.toInt(),
  title = title,
  backDrop = backdrop,
  genres = genres.map { it.id },
  poster = poster,
  rating = rating,
  votes = votes,
  releaseDate = releaseDate,
  overview = overview
)