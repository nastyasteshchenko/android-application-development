package ru.nsu.currency.converter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import ru.nsu.currency.converter.Application
import ru.nsu.currency.converter.databinding.FragmentCurrencyListBinding
import ru.nsu.currency.converter.presentation.CurrencyViewModel
import ru.nsu.currency.converter.ui.adapter.recyclerview.CurrencyRecycleViewAdapter
import ru.nsu.currency.converter.ui.adapter.recyclerview.SpaceItemDecoration
import javax.inject.Inject

class CurrencyListFragment @Inject constructor() : Fragment() {

    companion object {
        private const val VERTICAL_SPACE_HEIGHT_DP = 16
    }

    @Inject
    lateinit var viewModelFactory: CurrencyViewModel.ViewModelFactory

    private val viewModel: CurrencyViewModel by activityViewModels { viewModelFactory }

    private val binding: FragmentCurrencyListBinding by lazy {
        FragmentCurrencyListBinding.inflate(
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

        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        val adapter = CurrencyRecycleViewAdapter(this.requireContext())
        binding.recyclerView.adapter = adapter
        val spaceDecoration =
            SpaceItemDecoration(
                (VERTICAL_SPACE_HEIGHT_DP * resources.displayMetrics.density)
                    .toInt()
            )
        binding.recyclerView.addItemDecoration(spaceDecoration)

        viewModel.currencies.observe(this.requireActivity()) {
            adapter.updateData(it)
        }
        //TODO do refresh only if needed
        viewModel.refreshCurrencies()
        viewModel.fetchCurrencies()

    }

    override fun onAttach(context: Context) {
        (requireActivity().application as Application).uiComponent.inject(this)
        super.onAttach(context)
    }
}