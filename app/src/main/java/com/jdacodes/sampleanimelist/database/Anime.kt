package com.jdacodes.sampleanimelist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "anime_table")
data class Anime(
    @PrimaryKey @ColumnInfo(name = "title")
    val title: String = "",
    val animeId: Int? = null,
    val imageUrl: String = ""
)

