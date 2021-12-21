package com.tomg.popcorn.db

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.PrimaryKey
import androidx.room.Query
import com.tomg.popcorn.api.Api
import io.reactivex.Maybe
import io.reactivex.Observable
import org.intellij.lang.annotations.Language

/** Could store this small data as textfiles but want to write some room **/
@Entity(tableName = "genres")
data class Genre(@PrimaryKey(autoGenerate = false) @ColumnInfo(name = "id") val id: Int,
                  @ColumnInfo(name = "name") val name: String)
@Dao
interface GenreDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertAll(articles: List<Genre>)

  @Query("SELECT * FROM genres")
  fun listAll(): Observable<List<Genre>>

  @Query("DELETE FROM genres")
  fun dropAll()

  @Language("RoomSql")
  @Query("SELECT * FROM genres WHERE id = :genreId")
  fun loadGenre(genreId: Int): Maybe<Genre>

  @Language("RoomSql")
  @Query("SELECT * FROM genres ORDER BY RANDOM() LIMIT 1")
  fun loadRandomGenre(): Maybe<Genre>
}

fun Api.Genre.toDbModel() = Genre(
  id = id,
  name = name
)

