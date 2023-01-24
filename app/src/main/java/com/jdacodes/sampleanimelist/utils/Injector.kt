package com.jdacodes.sampleanimelist.utils

import android.content.Context
import androidx.annotation.VisibleForTesting
import com.jdacodes.sampleanimelist.ui.animelist.AnimeRepository
import com.jdacodes.sampleanimelist.database.AnimeRoomDatabase
import com.jdacodes.sampleanimelist.network.NetworkService
import com.jdacodes.sampleanimelist.ui.animedetails.AnimeDetailsRepository
import com.jdacodes.sampleanimelist.ui.animedetails.AnimeDetailsViewModelFactory
import com.jdacodes.sampleanimelist.ui.animelist.AnimeListViewModelFactory

interface ViewModelFactoryProvider {
    fun provideAnimeListViewModelFactory(context: Context): AnimeListViewModelFactory
    fun provideAnimeDetailsViewModelFactory(context: Context): AnimeDetailsViewModelFactory
}

val Injector: ViewModelFactoryProvider
    get() = currentInjector

private object DefaultViewModelProvider : ViewModelFactoryProvider {
    private fun getAnimeRepository(context: Context): AnimeRepository {
        return AnimeRepository.getInstance(
            animeDao(context),
            animeService()
        )
    }
    private fun getAnimeDetailsRepository(context: Context): AnimeDetailsRepository {
        return AnimeDetailsRepository.getInstance(
            animeDao(context),
            animeService()
        )
    }

    private fun animeService() = NetworkService()

    private fun animeDao(context: Context) =
        AnimeRoomDatabase.getInstance(context.applicationContext).animeDao()

    override fun provideAnimeListViewModelFactory(context: Context): AnimeListViewModelFactory {
        val repository = getAnimeRepository(context)
        return AnimeListViewModelFactory(repository)
    }

    override fun provideAnimeDetailsViewModelFactory(context: Context): AnimeDetailsViewModelFactory {
        val repository = getAnimeDetailsRepository(context)
        return AnimeDetailsViewModelFactory(repository)
    }
}

private object Lock

@Volatile
private var currentInjector: ViewModelFactoryProvider =
    DefaultViewModelProvider

@VisibleForTesting
private fun setInjectorForTesting(injector: ViewModelFactoryProvider?) {
    synchronized(Lock) {
        currentInjector = injector ?: DefaultViewModelProvider
    }
}

@VisibleForTesting
private fun resetInjector() =
    setInjectorForTesting(null)