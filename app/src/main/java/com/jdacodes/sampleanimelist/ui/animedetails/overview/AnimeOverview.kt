package com.jdacodes.sampleanimelist.ui.animedetails.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import com.jdacodes.sampleanimelist.R
import com.jdacodes.sampleanimelist.databinding.FragmentAnimeOverviewBinding
import com.jdacodes.sampleanimelist.ui.animedetails.AnimeDetailsRepository
import com.jdacodes.sampleanimelist.ui.animedetails.AnimeDetailsViewModel
import com.jdacodes.sampleanimelist.utils.Injector
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeOverview : Fragment() {

    private var _binding: FragmentAnimeOverviewBinding? = null
    private val binding get() = _binding!!
    private lateinit var thirdPartyYouTubePlayerView: YouTubePlayerView

    private val viewModel: AnimeDetailsViewModel by viewModels(ownerProducer = { requireParentFragment() }) {
        Injector.provideAnimeDetailsChildViewModelFactory(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnimeOverviewBinding.inflate(inflater, container, false)
        context ?: return binding.root

        //show spinner when [spinner] is true
        viewModel.spinner.observe(viewLifecycleOwner, Observer { show ->
            binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
            binding.parentContainer.visibility = if (show) View.GONE else View.VISIBLE
        })

        // Show a snackbar whenever the [snackbar] is updated a non-null value
        viewModel.snackbar.observe(viewLifecycleOwner, Observer { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        })

        thirdPartyYouTubePlayerView = binding.animeOverviewTrailer
        // We set it to false because we init it manually
        thirdPartyYouTubePlayerView.enableAutomaticInitialization = false


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    binding.viewModel = viewModel
                    val animeId = viewModel.animeIdLiveData.value.toString()
                    Log.d("AnimeOverview", animeId)

                    val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            // We're using pre-made custom ui
                            val defaultPlayerUiController =
                                DefaultPlayerUiController(thirdPartyYouTubePlayerView, youTubePlayer)
                            defaultPlayerUiController.showFullscreenButton(true)

                            // When the video is in full-screen, cover the entire screen
                            defaultPlayerUiController.setFullScreenButtonClickListener {
                                if (thirdPartyYouTubePlayerView.isFullScreen()) {
                                    thirdPartyYouTubePlayerView.exitFullScreen()
//                        Window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
//                        // Show ActionBar
//                        if (supportActionBar != null) {
//                            supportActionBar!!.show()
//                        }
                                }else {
                                    thirdPartyYouTubePlayerView.enterFullScreen()
//                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
//                        // Hide ActionBar
//                        if (supportActionBar != null) {
//                            supportActionBar!!.hide()
                                }
                            }
                            thirdPartyYouTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
                            viewModel.animeDetailsLiveData.observeForever(Observer { anime ->

                                //24T6YgLUS9A
                                val videoId = anime.youtubeId
                                youTubePlayer.cueVideo(videoId, 0f)
                            })
                        }
                    }
                    // Disable iFrame UI
                    val options: IFramePlayerOptions = IFramePlayerOptions.Builder().controls(0).build()
                    thirdPartyYouTubePlayerView.initialize(listener, options)

                } catch (throwable: Throwable) {
                    Log.d("AnimeOverview", "throwable: " + throwable.message.toString())
                }
            }
        }
    }


}


/**
 * Factory for creating a [AnimeDetailsChildViewModel] with a constructor that takes a [AnimeDetailsRepository].
 */
class AnimeDetailsChildViewModelFactory(
    private val repository: AnimeDetailsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AnimeDetailsViewModel(repository) as T
}