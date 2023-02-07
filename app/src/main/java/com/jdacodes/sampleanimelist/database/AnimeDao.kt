package com.jdacodes.sampleanimelist.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.jdacodes.sampleanimelist.database.Anime
import kotlinx.coroutines.flow.Flow

@Dao
interface AnimeDao {
    @Query("SELECT * FROM anime_table")
    fun getAnimeList(): Flow<List<Anime>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(animes: List<Anime>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllStudios(studios: List<StudioEntity>)

    @Query("SELECT * FROM anime_table WHERE animeId = :animeId")
    fun getAnimeDetails(animeId: Int): Flow<Anime>

    @Query("SELECT * FROM studio_table")
    fun getAnimeStudios(): Flow<StudioEntity>


}