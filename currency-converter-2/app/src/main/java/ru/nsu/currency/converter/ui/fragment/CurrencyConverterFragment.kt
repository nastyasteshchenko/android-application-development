package ru.nsu.currency.converter.ui.fragment

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import ru.nsu.currency.converter.Application
import ru.nsu.currency.converter.R
import ru.nsu.currency.converter.databinding.FragmentCurrencyConverterBinding
import ru.nsu.currency.converter.domain.exception.WrongRubAmountException
import ru.nsu.currency.converter.domain.model.Currency
import ru.nsu.currency.converter.presentation.CurrencyViewModel
import ru.nsu.currency.converter.ui.adapter.spinner.CurrencyItemSelectedListener
import ru.nsu.currency.converter.ui.adapter.spinner.CurrencySpinnerAdapter
import java.text.DecimalFormat
import javax.inject.Inject

class CurrencyConverterFragment @Inject constructor() : Fragment() {

    companion object {
        private const val SAVED_RUB_AMOUNT_KEY = "saved_rub_amount"
        private const val SAVED_SELECTED_CURRENCY_KEY = "saved_selected_currency"
    }

    private val formatter = DecimalFormat("#,###.##")

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

        binding.currencySpinner.onItemSelectedListener =
            CurrencyItemSelectedListener { onConvertCurrency(it) }

        savedInstanceState?.let { oit ->
            val savedRubAmount = oit.getString(SAVED_RUB_AMOUNT_KEY)
            val savedCurrency =
                oit.getSerializable(SAVED_SELECTED_CURRENCY_KEY, Currency::class.java)

            binding.rubAmount.setText(savedRubAmount)
            savedCurrency?.let { onConvertCurrency(it) }
        }

        binding.rubAmount.addTextChangedListener(object : TextWatcher {
            private var currentText = ""
            private val formatter = DecimalFormat("#,###.#########")

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                onConvertCurrency(binding.currencySpinner.selectedItem as Currency)
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString() == currentText) {
                    return
                }
                if (s.toString().contains(".", true)) {
                    currentText = s.toString()
                    setCurrentText()
                    return
                }
                val cleanString = s.toString().replace(",", "")
                if (cleanString.isNotEmpty()) {
                    currentText = formatCurrency(cleanString)
                    setCurrentText()
                }
            }

            private fun setCurrentText() {
                binding.rubAmount.setText(currentText)
                binding.rubAmount.setSelection(currentText.length)
            }

            private fun formatCurrency(value: String): String {
                val parsed = value.toDoubleOrNull() ?: 0.0
                return formatter.format(parsed)
            }
        })
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as Application).uiComponent.inject(this)
        super.onAttach(context)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(SAVED_RUB_AMOUNT_KEY, binding.rubAmount.text.toString())
        val selectedCurrency = binding.currencySpinner.selectedItem as? Currency
        outState.putSerializable(SAVED_SELECTED_CURRENCY_KEY, selectedCurrency)
    }

    private fun onConvertCurrency(currency: Currency) {
        val rubAmount = binding.rubAmount.text.toString()
            .replace(",", "")
            .toDoubleOrNull()
        try {
            val convertedValue = viewModel.convertCurrencyFromRubs(rubAmount, currency)
            binding.conversionResult.text = this.getString(
                R.string.conversion_result_template,
                formatter.format(convertedValue),
                currency.charCode
            )
        } catch (e: WrongRubAmountException) {
            binding.conversionResult.text = e.message
        }
    }
}
