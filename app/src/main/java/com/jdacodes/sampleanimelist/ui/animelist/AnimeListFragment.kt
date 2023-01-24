package com.jdacodes.sampleanimelist.ui.animelist

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

import com.jdacodes.sampleanimelist.databinding.FragmentAnimeListBinding
import com.jdacodes.sampleanimelist.utils.Injector


class AnimeListFragment : Fragment() {

    private val viewModel: AnimeViewModel by viewModels {
        Injector.provideAnimeListViewModelFactory(requireContext())

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentAnimeListBinding.inflate(inflater, container, false)
        context ?: return binding.root

        //show spinner when [spinner] is true
        viewModel.spinner.observe(viewLifecycleOwner) { show ->
            binding.spinner.visibility = if (show) View.VISIBLE else View.GONE
        }

        // Show a snackbar whenever the [snackbar] is updated a non-null value
        viewModel.snackbar.observe(viewLifecycleOwner) { text ->
            text?.let {
                Snackbar.make(binding.root, text, Snackbar.LENGTH_SHORT).show()
                viewModel.onSnackbarShown()
            }
        }

        val adapter = AnimeAdapter(AnimeItemListener { animeId ->
//            Toast.makeText(context, "${animeId}", Toast.LENGTH_LONG).show()
            viewModel.onAnimeItemClicked(animeId)
        })
        binding.animeList.adapter = adapter
        subscribeUi(adapter)
        return binding.root

    }

    private fun subscribeUi(adapter: AnimeAdapter) {
        viewModel.animesUsingFlow.observe(viewLifecycleOwner) { animes ->
            adapter.submitList(animes)
        }
        viewModel.navigateToAnimeDetail.observe(viewLifecycleOwner, Observer { animeId ->
            animeId?.let {
                this.findNavController().navigate(
                    AnimeListFragmentDirections.actionAnimeListFragmentToAnimeDetailsFragment(
                        animeId
                    )
                )
                viewModel.onAnimeDetailNavigated()
            }
        })
    }

}

/**
 * Factory for creating a [AnimeViewModel] with a constructor that takes a [AnimeRepository].
 */
class AnimeListViewModelFactory(
    private val repository: AnimeRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>) = AnimeViewModel(repository) as T
}