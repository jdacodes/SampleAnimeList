package com.jdacodes.sampleanimelist.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdacodes.sampleanimelist.model.Genre
import com.jdacodes.sampleanimelist.model.Studio

class DataConverter {
    @TypeConverter
    fun fromAnimeStudiosList(value: List<Studio>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Studio>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAnimeStudiosList(value: String): List<Studio> {
        val gson = Gson()
        val type = object : TypeToken<List<Studio>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromAnimeGenresList(value: List<Genre>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAnimeGenresList(value: String): List<Genre> {
        val gson = Gson()
        val type = object : TypeToken<List<Genre>>() {}.type
        return gson.fromJson(value, type)
    }
}