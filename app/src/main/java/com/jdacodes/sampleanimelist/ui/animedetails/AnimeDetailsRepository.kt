package com.jdacodes.sampleanimelist.ui.animedetails

import com.jdacodes.sampleanimelist.database.Anime
import com.jdacodes.sampleanimelist.database.AnimeDao
import com.jdacodes.sampleanimelist.network.NetworkService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow

class AnimeDetailsRepository(
    private val animeDao: AnimeDao,
    private val animeService: NetworkService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    suspend fun getAnimeDetails(id: Int): Flow<Anime> {
        return animeDao.getAnimeDetails(id)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AnimeDetailsRepository? = null

        fun getInstance(animeDao: AnimeDao, animeService: NetworkService) =
            instance ?: synchronized(this) {
                instance ?: AnimeDetailsRepository(animeDao, animeService).also { instance = it }
            }
    }
}
