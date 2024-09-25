package ru.nsu.currency.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.currency.converter.model.Currency

class MainActivity : AppCompatActivity(), OnCurrencyClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var currencyAdapter: CurrencyAdapter
    private lateinit var rubAmount: EditText
    private lateinit var selectedCurrency: TextView
    private lateinit var conversionResult: TextView

    private var currentCurrency: Currency? = null
    private val currencyDataTaker = CurrencyDataTaker()
    private val currencyConverter: CurrencyConverter = CurrencyConverter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        rubAmount = findViewById(R.id.rubAmount)
        selectedCurrency = findViewById(R.id.selectedCurrency)
        conversionResult = findViewById(R.id.conversionResult)

        rubAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                currentCurrency?.let { onConvertCurrency(it) }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        setCurrencyList()
    }

    override fun onConvertCurrency(currency: Currency) {
        currentCurrency = currency
        selectedCurrency.text =
            this.getString(R.string.selected_currency, currency.name, currency.charCode)
        val rubAmount = rubAmount.text.toString().toDoubleOrNull()
        if (rubAmount == null) {
            conversionResult.text = this.getString(R.string.conversion_error)
            return
        }
        val convertedValue = currencyConverter.convertFromRubs(rubAmount, currency)
        conversionResult.text =
            this.getString(R.string.conversion_result, convertedValue, currency.charCode)
    }

    private fun setCurrencyList() {
        Thread {
            val currency = currencyDataTaker.getCurrencyList()
            runOnUiThread {
                currencyAdapter = CurrencyAdapter(currency, this, this)
                recyclerView.adapter = currencyAdapter
            }
        }.start()
    }

}