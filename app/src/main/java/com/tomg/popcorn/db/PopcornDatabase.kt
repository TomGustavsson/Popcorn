package com.tomg.popcorn.db

import androidx.room.Database
import androidx.room.ProvidedTypeConverter
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.tomg.popcorn.api.Api
import javax.inject.Inject

@Database(
  entities = [(Genre::class), (Favourite::class)],
  version = 1,
  exportSchema = false
)
@TypeConverters(Converters::class)
abstract class PopcornDatabase: RoomDatabase() {

  abstract fun genreDao() : GenreDao
  abstract fun favouriteDao(): FavouriteDao
}

@ProvidedTypeConverter
class Converters
@Inject constructor(moshi: Moshi){

  private val adapter = moshi.adapter<List<Int>>(Types.newParameterizedType(List::class.java, Integer::class.java))

  @TypeConverter
  fun listToJson(value: List<Int>?) = adapter.toJson(value)

  @TypeConverter
  fun jsonToList(value: String) = adapter.fromJson(value)?.toList()
}