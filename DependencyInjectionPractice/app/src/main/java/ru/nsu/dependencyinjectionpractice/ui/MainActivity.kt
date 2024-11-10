package ru.nsu.dependencyinjectionpractice.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.dependencyinjectionpractice.HomeworkApplication
import ru.nsu.dependencyinjectionpractice.databinding.ActivityMainBinding
import ru.nsu.dependencyinjectionpractice.presentation.MainState
import ru.nsu.dependencyinjectionpractice.presentation.MainViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: MainViewModel.ViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as HomeworkApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        setContentView(binding.root)

        binding.loadButton.setOnClickListener {
            viewModel.loadStrings()
        }

        viewModel.state.observe(this) { newState ->
            renderState(newState)
        }
    }

    private fun renderState(state: MainState) {
        when (state) {
            MainState.Loading -> {
                Toast.makeText(this, "loading", Toast.LENGTH_SHORT).show()
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