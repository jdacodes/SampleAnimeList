package com.jdacodes.sampleanimelist.ui.animedetails

import androidx.lifecycle.*
import com.jdacodes.sampleanimelist.database.Anime
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class AnimeDetailsViewModel internal constructor(
    private val repository: AnimeDetailsRepository,
    private val animeId: Int = 0
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

    suspend fun retrieveAnimeDetails(id: Int): LiveData<Anime> {
        return repository.getAnimeDetails(id).asLiveData()
    }

    //retrieve anime detail object
    val _animeDetailsLiveData = MutableLiveData<Anime>()
    val animeDetailsLiveData: LiveData<Anime>
        get() = _animeDetailsLiveData

    val _animeIdLiveData = MutableLiveData<Int>()
    val animeIdLiveData: LiveData<Int>
        get() = _animeIdLiveData

    init {
        launchDataLoad {

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
            } finally {
                _spinner.value = false
            }
        }
    }
}
