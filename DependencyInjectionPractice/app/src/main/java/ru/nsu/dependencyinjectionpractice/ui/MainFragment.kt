package ru.nsu.dependencyinjectionpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.nsu.dependencyinjectionpractice.databinding.FragmentMainBinding
import ru.nsu.dependencyinjectionpractice.presentation.MainState
import ru.nsu.dependencyinjectionpractice.presentation.MainViewModel
import javax.inject.Inject

class MainFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.ViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private val binding: FragmentMainBinding by lazy {
        FragmentMainBinding.inflate(
            LayoutInflater.from(
                this.requireContext()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.loadButton.setOnClickListener {
            viewModel.loadStrings()
        }

        viewModel.state.observe(viewLifecycleOwner) { newState ->
            renderState(newState)
        }
    }

    private fun renderState(state: MainState) {
        when (state) {
            MainState.Loading -> {
                binding.remoteText.text = ""
                binding.localText.text = ""
            }

            is MainState.Success -> {
                binding.remoteText.text = state.remoteString
                binding.localText.text = state.localString
            }
        }
    }
}