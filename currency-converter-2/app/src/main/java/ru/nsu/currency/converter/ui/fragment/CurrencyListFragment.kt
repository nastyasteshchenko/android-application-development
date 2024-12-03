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
import ru.nsu.currency.converter.R
import ru.nsu.currency.converter.databinding.FragmentCurrencyListBinding
import ru.nsu.currency.converter.domain.model.Currency
import ru.nsu.currency.converter.presentation.CurrencyViewModel
import ru.nsu.currency.converter.ui.adapter.recyclerview.CurrencyRecycleViewAdapter
import ru.nsu.currency.converter.ui.adapter.recyclerview.SpaceItemDecoration
import javax.inject.Inject

class CurrencyListFragment @Inject constructor() : Fragment() {

    companion object {
        private const val VERTICAL_SPACE_HEIGHT_DP = 16
        private const val SAVED_CURRENCY_LIST_KEY = "saved_currency_list"
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

    @Suppress("UNCHECKED_CAST")
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

        savedInstanceState?.let {
            val savedCurrencies =
                it.getSerializable(
                    SAVED_CURRENCY_LIST_KEY,
                    ArrayList::class.java
                ) as? ArrayList<Currency>
            savedCurrencies?.let { adapter.updateData(it) }
        }

        viewModel.currencies.observe(this.requireActivity()) {
            adapter.updateData(it)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        binding.swipeRefreshLayout
            .setColorSchemeResources(
                R.color.cannon_pink,
                R.color.light_orange
            )

        binding.swipeRefreshLayout.setOnRefreshListener {
            viewModel.refreshCurrencies()
        }
        //TODO do refresh only if needed

        if (savedInstanceState == null) {
            viewModel.fetchCurrencies()
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val currentCurrencies = viewModel.currencies.value
        if (currentCurrencies != null) {
            outState.putSerializable(SAVED_CURRENCY_LIST_KEY, ArrayList(currentCurrencies))
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as Application).uiComponent.inject(this)
        super.onAttach(context)
    }
}