package com.jdacodes.sampleanimelist.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("studio_table")
data class StudioEntity(
    @PrimaryKey @ColumnInfo(name = "studioId")
    val studioId: Int? = null,
    val name: String = "",
    val type: String = "",
    val url: String = ""
)
