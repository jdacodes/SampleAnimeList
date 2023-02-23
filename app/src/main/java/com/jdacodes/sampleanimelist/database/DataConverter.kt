package com.jdacodes.sampleanimelist.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.jdacodes.sampleanimelist.model.*

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


    @TypeConverter
    fun fromAnimeDemographicsList(value: List<Demographic>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Demographic>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAnimeDemographicsList(value: String): List<Demographic> {
        val gson = Gson()
        val type = object : TypeToken<List<Demographic>>() {}.type
        return gson.fromJson(value, type)
    }

    @TypeConverter
    fun fromAnimeThemeList(value: List<Theme>): String {
        val gson = Gson()
        val type = object : TypeToken<List<Theme>>() {}.type
        return gson.toJson(value, type)
    }

    @TypeConverter
    fun toAnimeThemeList(value: String): List<Theme> {
        val gson = Gson()
        val type = object : TypeToken<List<Theme>>() {}.type
        return gson.fromJson(value, type)
    }

//    @TypeConverter
//    fun fromAnimeBroadcast(value: Broadcast): String {
//        val gson = Gson()
//        val type = object : TypeToken<Broadcast>() {}.type
//        return gson.toJson(value, type)
//    }
//
//    @TypeConverter
//    fun toAnimeBroadcast(value: String): Broadcast {
//        val gson = Gson()
//        val type = object : TypeToken<Broadcast>() {}.type
//        return gson.fromJson(value, type)
//    }
}