package com.jdacodes.sampleanimelist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.jdacodes.sampleanimelist.model.*

@Entity(tableName = "anime_table")
data class Anime(
    @PrimaryKey @ColumnInfo(name = "title")
    val title: String = "",
    val titleJapanese: String = "",
    val type: String = "",
    val source: String = "",
    val status: String ="",
    val aired: String = "",
    val duration: String = "",
    val rating: String = "",
    val score: Double? = null,
    val animeId: Int? = null,
    val imageUrl: String = "",
    val synopsis: String = "",
    val studios: List<Studio>,
    val genre: List<Genre>,
    val producers: List<Producer>,
    val licensors: List<Licensor>,
    val rank: Int? = null,
    val popularity: Int? = null,
    val members: Int? = null,
    val favorites: Int? = null,
    val broadcast: String = "",
    val episodes: Int? = null,
    val year: Int? = null,
    val youtubeId: String = "",
    val season: String = "",
    val url: String = "",
    val demographics: List<Demographic>,
    val themes: List<Theme>



    )

