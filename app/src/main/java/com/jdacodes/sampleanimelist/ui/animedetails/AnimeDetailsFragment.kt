package com.jdacodes.sampleanimelist.ui.animedetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import androidx.navigation.fragment.navArgs
import com.jdacodes.sampleanimelist.database.Anime

import com.jdacodes.sampleanimelist.databinding.FragmentAnimeDetailsBinding
import com.jdacodes.sampleanimelist.databinding.FragmentAnimeDetailsNewBinding
import com.jdacodes.sampleanimelist.utils.Injector
import kotlinx.coroutines.launch

class AnimeDetailsFragment : Fragment() {
    val args: AnimeDetailsFragmentArgs by navArgs()

    private lateinit var anime: Anime

    private var _binding: FragmentAnimeDetailsNewBinding? = null
    private val binding get() = _binding!!

    private val viewModel: AnimeDetailsViewModel by viewModels {
        Injector.provideAnimeDetailsViewModelFactory(requireContext())

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
        val animeId = args.animeId
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.retrieveAnimeDetails(animeId)
                    .observe(viewLifecycleOwner, Observer { selectedAnime ->
                        anime = selectedAnime
                        binding.anime = anime

                    })
            }
        }

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