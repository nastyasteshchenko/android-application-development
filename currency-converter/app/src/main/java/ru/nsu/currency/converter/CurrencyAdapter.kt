package ru.nsu.currency.converter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.currency.converter.model.Currency

class CurrencyAdapter(
    private val currencyList: List<Currency>,
    private val listener: OnCurrencyClickListener,
    private val context : Context,
) : RecyclerView.Adapter<CurrencyAdapter.CurrencyViewHolder>() {

    class CurrencyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val currencyName: TextView = itemView.findViewById(R.id.currencyName)
        val currencyValue: TextView = itemView.findViewById(R.id.currencyValue)
        val currencyPrevValue: TextView = itemView.findViewById(R.id.currencyPrevValue)
        val currencyCharCode: TextView = itemView.findViewById(R.id.currencyCharCode)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.currency_item, parent, false)
        return CurrencyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) {
        val currency = currencyList[position]

        holder.currencyName.text = context.getString(R.string.currency_name, currency.name)
        holder.currencyValue.text = context.getString(R.string.value, currency.value)
        holder.currencyCharCode.text = context.getString(R.string.char_code, currency.charCode)
        holder.currencyPrevValue.text = context.getString(R.string.previous_value, currency.prevValue)

        holder.itemView.setOnClickListener {
            listener.onConvertCurrency(currency)
        }
    }

    override fun getItemCount() = currencyList.size
}
