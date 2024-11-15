package com.jdacodes.sampleanimelist.ui.animedetails

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayoutMediator
import com.jdacodes.sampleanimelist.R
import com.jdacodes.sampleanimelist.database.Anime

import com.jdacodes.sampleanimelist.databinding.FragmentAnimeDetailsNewBinding
import com.jdacodes.sampleanimelist.utils.Injector
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AnimeDetailsFragment : Fragment() {
    val args: AnimeDetailsFragmentArgs by navArgs()

    private lateinit var anime: Anime

    private var _binding: FragmentAnimeDetailsNewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnimeDetailsViewModel by viewModels {
        Injector.provideAnimeDetailsViewModelFactory(requireContext())

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAnimeDetailsNewBinding.inflate(inflater, container, false)
        context ?: return binding.root

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                try {
                    binding.viewPager.offscreenPageLimit = 3
                    val animeId = args.animeId
                    viewModel._animeIdLiveData.value = animeId
                    Log.d(
                        "AnimeDetailsFragment",
                        "animeIdLiveData: ${viewModel._animeIdLiveData.value.toString()}"
                    )
                    setupViewPager()
                    setupTabLayout()
                    viewModel.retrieveAnimeDetails(animeId)
                        .observe(viewLifecycleOwner, Observer { selectedAnime ->
                            anime = selectedAnime
                            Log.d("AnimeDetailsFragment", "Anime: $anime")
                            viewModel._animeDetailsLiveData.value = anime
                            Log.d(
                                "AnimeDetailsFragment",
                                "AnimeLiveData: " +
                                        viewModel.animeDetailsLiveData.value.toString()
                            )
                            binding.anime = anime
                        })
                } catch (throwable: Throwable) {
                    Log.d("AnimeDetailsFragment", "throwable: " + throwable.message.toString())
                }
            }
        }
    }

    private fun setupTabLayout() {
        TabLayoutMediator(
            binding.tabLayout, binding.viewPager
        ) { tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.anime_overview_label)
                1 -> tab.text = getString(R.string.anime_staff_label)
            }
        }.attach()
    }

    private fun setupViewPager() {
        val adapter = AnimeDetailsAdapter(this@AnimeDetailsFragment, 2)
        binding.viewPager.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}

/**
 * Factory for creating a [AnimeDetailsViewModel] with a constructor that takes a [AnimeDetailsRepository].
 */
class AnimeDetailsViewModelFactory(
    private val repository: AnimeDetailsRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) =
        AnimeDetailsViewModel(repository) as T
}