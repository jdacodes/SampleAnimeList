package com.jdacodes.sampleanimelist.ui.animelist

import android.util.Log
import androidx.lifecycle.*
import com.jdacodes.sampleanimelist.database.Anime
import com.jdacodes.sampleanimelist.database.StudioEntity
import com.jdacodes.sampleanimelist.network.NetworkResult
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

/**
 * The [ViewModel] for fetching a list of [Anime]s.
 */
@Suppress("UNCHECKED_CAST")
class AnimeViewModel internal constructor(
    private val animeRepository: AnimeRepository
) : ViewModel() {

    /**
     * Request a snackbar to display a string.
     */
    private val _snackbar = MutableLiveData<String?>()

    /**
     * Request a snackbar to display a string.
     */
    val snackbar: LiveData<String?>
        get() = _snackbar

    private val _spinner = MutableLiveData<Boolean>(false)

    /**
     * Show a loading spinner if true
     */
    val spinner: LiveData<Boolean>
        get() = _spinner

    /**
     * List of animes using flow that is converted to LiveData
     */

    val animesUsingFlow: LiveData<List<Anime>> = animeRepository.animeUsingFlow.asLiveData()

    val studiosUsingFlow: LiveData<StudioEntity> = animeRepository.studioUsingFlow.asLiveData()

    /**
     * Navigate to AnimeDetails fragment
     */

    private val _navigateToAnimeDetail = MutableLiveData<Int?>()
    val navigateToAnimeDetail
        get() = _navigateToAnimeDetail

    fun onAnimeItemClicked(id: Int) {
        _navigateToAnimeDetail.value = id
    }

    fun onAnimeDetailNavigated() {
        _navigateToAnimeDetail.value = null
    }


    init {
        launchDataLoad {
            startDataLoad()
        }

    }

    /**
     * Called immediately after the UI shows the snackbar.
     */
    fun onSnackbarShown() {
        _snackbar.value = null
    }

    /**
     * Helper function to call a data load function with a loading spinner; errors will trigger a
     * snackbar.
     *
     * By marking [block] as [suspend] this creates a suspend lambda which can call suspend
     * functions.
     *
     * @param block lambda to actually load data. It is called in the viewModelScope. Before calling
     *              the lambda, the loading spinner will display. After completion or error, the
     *              loading spinner will stop.
     */
    private fun launchDataLoad(block: suspend () -> Unit): Job {
        return viewModelScope.launch {
            try {
                _spinner.value = true
                block()
            } catch (error: Throwable) {
                _snackbar.value = error.message
                Log.d("AnimeViewModel", "launchDataLoad: error message: ${error.message}")
            } finally {
                _spinner.value = false
            }
        }
    }

    private suspend fun startDataLoad() {

        when (val response = animeRepository.fetchAnimeList()) {
            is NetworkResult.Success -> {
                animeRepository.insertAnimeList(response.data)
                Log.d("AnimeViewModel", "startDataLoad:success")
            }
            is NetworkResult.Error -> {
                _snackbar.postValue("${response.code} ${response.message}")
                Log.d("AnimeViewModel", "startDataLoad: Error ${response.code} ${response.message}")
            }
            is NetworkResult.Exception -> {
                _snackbar.postValue("${response.e.message}")
                Log.d("AnimeViewModel", "startDataLoad: Exception ${response.e.message}")
            }
        }


    }
}