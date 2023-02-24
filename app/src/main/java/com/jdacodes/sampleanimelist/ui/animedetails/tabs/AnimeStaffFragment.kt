package com.jdacodes.sampleanimelist.ui.animedetails.tabs

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.snackbar.Snackbar
import com.jdacodes.sampleanimelist.databinding.FragmentAnimeOverviewBinding
import com.jdacodes.sampleanimelist.databinding.FragmentAnimeStaffBinding
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

class AnimeStaffFragment: Fragment() {

    private var _binding: FragmentAnimeStaffBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnimeDetailsViewModel by viewModels(ownerProducer = { requireParentFragment() }) {
        Injector.provideAnimeDetailsChildViewModelFactory(requireContext())

    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnimeStaffBinding.inflate(inflater, container, false)
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

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.Default) {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    binding.viewModel = viewModel
                    val animeId = viewModel.animeIdLiveData.value.toString()
                    Log.d("AnimeStaff", animeId)

//                    val listener: YouTubePlayerListener = object : AbstractYouTubePlayerListener() {
//                        override fun onReady(youTubePlayer: YouTubePlayer) {
//                            // We're using pre-made custom ui
//                            val defaultPlayerUiController =
//                                DefaultPlayerUiController(
//                                    thirdPartyYouTubePlayerView,
//                                    youTubePlayer
//                                )
//                            defaultPlayerUiController.showFullscreenButton(true)
//
//                            // When the video is in full-screen, cover the entire screen
//                            defaultPlayerUiController.setFullScreenButtonClickListener {
//                                if (thirdPartyYouTubePlayerView.isFullScreen()) {
//                                    thirdPartyYouTubePlayerView.exitFullScreen()
////                        Window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_VISIBLE
////                        // Show ActionBar
////                        if (supportActionBar != null) {
////                            supportActionBar!!.show()
////                        }
//                                } else {
//                                    thirdPartyYouTubePlayerView.enterFullScreen()
////                        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
////                        // Hide ActionBar
////                        if (supportActionBar != null) {
////                            supportActionBar!!.hide()
//                                }
//                            }
//                            thirdPartyYouTubePlayerView.setCustomPlayerUi(defaultPlayerUiController.rootView)
//                            viewModel.animeDetailsLiveData.observeForever(Observer { anime ->
//
//                                //24T6YgLUS9A
//                                val videoId = anime.youtubeId
//                                youTubePlayer.cueVideo(videoId, 0f)
//                            })
//                        }
//                    }
                    // Disable iFrame UI
//                    val options: IFramePlayerOptions =
//                        IFramePlayerOptions.Builder().controls(0).build()
//                    thirdPartyYouTubePlayerView.initialize(listener, options)

                } catch (throwable: Throwable) {
                    Log.d("AnimeStaff", "throwable: " + throwable.message.toString())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}