package ru.nsu.currency.converter.ui.adapter.spinner

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import ru.nsu.currency.converter.R
import ru.nsu.currency.converter.domain.model.Currency

class CurrencySpinnerAdapter(
    context: Context,
    private val currencies: List<Currency>
) : ArrayAdapter<Currency>(context, R.layout.spinner_item, currencies) {

    private val inflater = LayoutInflater.from(context)

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item, parent, false)

        val textView = view.findViewById<TextView>(R.id.currencyNameSpinner)
        val currency = currencies[position]
        textView.text = context.getString(
            R.string.currency_name_with_charcode,
            currency.name,
            currency.charCode
        )

        return view
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = convertView ?: inflater.inflate(R.layout.spinner_item, parent, false)

        val textView = view.findViewById<TextView>(R.id.currencyNameSpinner)
        val currency = currencies[position]
        textView.text = context.getString(
            R.string.currency_name_with_charcode,
            currency.name,
            currency.charCode
        )

        val separator = view.findViewById<View>(R.id.separator)
        if (position == currencies.size - 1) {
            separator.visibility = View.GONE
        } else {
            separator.visibility = View.VISIBLE
        }

        return view
    }
}
