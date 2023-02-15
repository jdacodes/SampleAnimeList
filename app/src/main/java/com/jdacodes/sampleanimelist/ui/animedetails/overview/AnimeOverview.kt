package com.jdacodes.sampleanimelist.ui.animedetails.overview

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.*
import com.jdacodes.sampleanimelist.databinding.FragmentAnimeOverviewBinding
import com.jdacodes.sampleanimelist.ui.animedetails.AnimeDetailsFragment
import com.jdacodes.sampleanimelist.ui.animedetails.AnimeDetailsRepository
import com.jdacodes.sampleanimelist.ui.animedetails.AnimeDetailsViewModel
import com.jdacodes.sampleanimelist.utils.Injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeOverview : Fragment() {

    private var _binding: FragmentAnimeOverviewBinding? = null
    private val binding get() = _binding!!

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

//        setupData()
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
                } catch (throwable: Throwable) {
                    Log.d("AnimeOverview", "throwable: " + throwable.message.toString())
                }
            }
        }


    }

    private fun setupData() {

//        viewModel._animeDetailsLiveData.observe(viewLifecycleOwner,
//            Observer { anime ->
//                Log.d("AnimeOverview", anime.synopsis)
//            binding.animeItemSynopsis.text = anime.synopsis
//            })
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