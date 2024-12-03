package ru.nsu.currency.converter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.nsu.currency.converter.Application
import ru.nsu.currency.converter.R
import ru.nsu.currency.converter.databinding.FragmentCurrencyConverterBinding
import ru.nsu.currency.converter.domain.model.Currency
import ru.nsu.currency.converter.presentation.CurrencyViewModel
import ru.nsu.currency.converter.ui.adapter.spinner.CurrencySpinnerAdapter
import javax.inject.Inject

class CurrencyConverterFragment @Inject constructor() : Fragment() {

    @Inject
    lateinit var viewModelFactory: CurrencyViewModel.ViewModelFactory

    private val viewModel: CurrencyViewModel by activityViewModels { viewModelFactory }

    private val binding: FragmentCurrencyConverterBinding by lazy {
        FragmentCurrencyConverterBinding.inflate(
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

        binding.currencySpinner.adapter = CurrencySpinnerAdapter(
            this.requireContext(),
            viewModel.getCurrencies()
        )

        binding.currencySpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedCurrency = parent?.getItemAtPosition(position) as Currency
                onConvertCurrency(selectedCurrency)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }
        }

        binding.rubAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onConvertCurrency(binding.currencySpinner.selectedItem as Currency)
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    fun onConvertCurrency(currency: Currency) {
        val rubAmount = getEnteredRubAmount() ?: return
        val convertedValue = convertToCurrentCurrency(rubAmount, currency)
        updateConversionResultText(convertedValue, currency)
    }

    private fun updateConversionResultText(convertedValue: Double?, currency: Currency) {
        binding.conversionResult.text =
            this.getString(
                R.string.conversion_result,
                convertedValue,
                currency.charCode
            )
    }

    private fun getEnteredRubAmount(): Double? {
        val rubAmount = binding.rubAmount.text.toString().toDoubleOrNull()
        if (rubAmount == null) {
            binding.conversionResult.text = this.getString(R.string.conversion_error)
            return null
        }
        return rubAmount
    }

    private fun convertToCurrentCurrency(rubAmount: Double, currency: Currency): Double {
        return viewModel.convertCurrencyFromRubs(rubAmount, currency)
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as Application).uiComponent.inject(this)
        super.onAttach(context)
    }
}