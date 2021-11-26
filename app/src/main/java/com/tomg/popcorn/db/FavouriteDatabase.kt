package com.tomg.popcorn.db

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
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
                     @ColumnInfo(name = "releasedate") val releaseDate: String,
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