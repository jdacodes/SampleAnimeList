package com.jdacodes.sampleanimelist.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

/**
 * The Room database for this app
 */
@Database(entities = [Anime::class,StudioEntity::class], version = 9, exportSchema = false)
@TypeConverters(DataConverter::class)
abstract class AnimeRoomDatabase : RoomDatabase() {

    abstract fun animeDao(): AnimeDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var instance: AnimeRoomDatabase? = null

        fun getInstance(context: Context): AnimeRoomDatabase {
            return instance ?: synchronized(this) {
                instance
                    ?: buildDatabase(
                        context
                    ).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AnimeRoomDatabase {
            return Room.databaseBuilder(context, AnimeRoomDatabase::class.java, DATABASE_NAME)
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}

private const val DATABASE_NAME = "anime-db"