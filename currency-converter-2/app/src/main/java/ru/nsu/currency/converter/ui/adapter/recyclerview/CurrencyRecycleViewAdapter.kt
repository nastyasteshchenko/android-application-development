package ru.nsu.currency.converter.ui.adapter.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.currency.converter.R
import ru.nsu.currency.converter.domain.model.Currency

class CurrencyRecycleViewAdapter(
    private val context: Context,
) : RecyclerView.Adapter<CurrencyRecycleViewAdapter.CurrencyViewHolder>() {

    private var currencies = ArrayList<Currency>()

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyName: TextView = itemView.findViewById(R.id.currencyName)
        val currencyNominal: TextView = itemView.findViewById(R.id.currencyNominal)
        val currencyValue: TextView = itemView.findViewById(R.id.currencyValue)
        val currencyPrevValue: TextView = itemView.findViewById(R.id.currencyPrevValue)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_item, parent, false)
        return CurrencyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencies[position]

        holder.currencyName.text = context.getString(
            R.string.currency_name_with_charcode,
            currency.name, currency.charCode
        )
        holder.currencyValue.text = context.getString(R.string.value, currency.value)
        holder.currencyPrevValue.text =
            context.getString(R.string.previous_value, currency.prevValue)
        holder.currencyNominal.text =
            context.getString(R.string.nominal, currency.nominal)
    }

    fun updateData(newItems: List<Currency>) {
        val diffCallback = CurrenciesDiffCallback(currencies, newItems)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        currencies.clear()
        currencies.addAll(newItems)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount() = currencies.size
}
