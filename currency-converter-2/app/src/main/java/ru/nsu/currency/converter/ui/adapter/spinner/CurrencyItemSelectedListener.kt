package ru.nsu.currency.converter.ui.adapter.spinner

import android.view.View
import android.widget.AdapterView
import ru.nsu.currency.converter.domain.model.Currency

class CurrencyItemSelectedListener(
    private val onItemSelectedAction: (Currency) -> Unit
) : AdapterView.OnItemSelectedListener {

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val selectedCurrency = parent?.getItemAtPosition(position) as? Currency
        if (selectedCurrency != null) {
            onItemSelectedAction(selectedCurrency)
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {}
}