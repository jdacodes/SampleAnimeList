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
import com.jdacodes.sampleanimelist.databinding.FragmentAnimeStaffBinding
import com.jdacodes.sampleanimelist.ui.animedetails.AnimeDetailsViewModel
import com.jdacodes.sampleanimelist.utils.Injector
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AnimeStaffFragment : Fragment() {

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