package com.jdacodes.sampleanimelist.ui.animelist

import com.jdacodes.sampleanimelist.database.Anime
import com.jdacodes.sampleanimelist.database.AnimeDao
import com.jdacodes.sampleanimelist.network.NetworkService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn

class AnimeRepository(
    private val animeDao: AnimeDao,
    private val animeService: NetworkService,
    private val defaultDispatcher: CoroutineDispatcher = Dispatchers.Default
) {
    /**
     * List of animes wrapped in Flow type from [AnimeDao]
     */
    val animeUsingFlow: Flow<List<Anime>> =
        animeDao.getAnimeList().flowOn(defaultDispatcher).conflate()

    /**
     * Fetch a new list of animes from the network and append them to [animeDao]
     */
    final suspend fun fetchAnimeList() {

        val animes = animeService.allAnimes().map {
            Anime(
                title = it.title, imageUrl = it.images.jpg.image_url, animeId = it.mal_id
            )
        }
        animeDao.insertAll(animes)
    }

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AnimeRepository? = null

        fun getInstance(animeDao: AnimeDao, animeService: NetworkService) =
            instance ?: synchronized(this) {
                instance ?: AnimeRepository(animeDao, animeService).also { instance = it }
            }
    }
}