package ru.nsu.currency.converter

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.nsu.currency.converter.model.Currency
import ru.nsu.currency.converter.ui.CurrencyAdapter
import java.io.IOException

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

        setCurrencyList()

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
    }

    override fun onConvertCurrency(currency: Currency) {
        currentCurrency = currency
        updateSelectCurrencyText()
        val rubAmount = getEnteredRubAmount() ?: return
        val convertedValue = convertToCurrentCurrency(rubAmount)
        updateConversionResultText(convertedValue)
    }

    private fun updateConversionResultText(convertedValue: Double?) {
        if (currentCurrency != null) {
            conversionResult.text =
                this.getString(
                    R.string.conversion_result,
                    convertedValue,
                    currentCurrency!!.charCode
                )
        }
    }

    private fun getEnteredRubAmount(): Double? {
        val rubAmount = rubAmount.text.toString().toDoubleOrNull()
        if (rubAmount == null) {
            conversionResult.text = this.getString(R.string.conversion_error)
            return null
        }
        return rubAmount
    }

    private fun convertToCurrentCurrency(rubAmount: Double): Double? {
        return currentCurrency?.let { currencyConverter.convertFromRubs(rubAmount, it) }
    }

    private fun updateSelectCurrencyText() {
        if (currentCurrency != null) {
            selectedCurrency.text =
                this.getString(
                    R.string.selected_currency,
                    currentCurrency!!.name,
                    currentCurrency!!.charCode
                )
        }
    }

    private fun setCurrencyList() {
        Thread {
            try {
                val currency = currencyDataTaker.getCurrencyList()
                runOnUiThread {
                    currencyAdapter = CurrencyAdapter(currency, this, this)
                    recyclerView.adapter = currencyAdapter
                }
            } catch (e: IOException) {
                runOnUiThread {
                    Toast.makeText(
                        this,
                        "Data acquisition error. Check your internet connection or restart the app",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }.start()
    }
}