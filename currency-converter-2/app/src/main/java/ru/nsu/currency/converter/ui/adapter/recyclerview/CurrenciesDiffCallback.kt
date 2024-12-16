package ru.nsu.currency.converter.ui.adapter.recyclerview

import androidx.recyclerview.widget.DiffUtil
import ru.nsu.currency.converter.domain.model.Currency

class CurrenciesDiffCallback(
    private val oldItems: List<Currency>,
    private val newItems: List<Currency>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldItems.size

    override fun getNewListSize(): Int = newItems.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean =
        oldItems[oldItemPosition].id == newItems[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }
}