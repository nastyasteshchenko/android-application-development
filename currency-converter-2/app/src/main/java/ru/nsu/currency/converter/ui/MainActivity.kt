package ru.nsu.currency.converter.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.add
import ru.nsu.currency.converter.R
import ru.nsu.currency.converter.databinding.ActivityMainBinding
import ru.nsu.currency.converter.ui.fragment.CurrencyConverterFragment
import ru.nsu.currency.converter.ui.fragment.CurrencyListFragment

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navCurrencyList -> {
                    supportFragmentManager.beginTransaction()
                        .add<CurrencyListFragment>(R.id.fragmentContainer)
                        .commit()
                    true
                }

                R.id.navConverter -> {
                    supportFragmentManager.beginTransaction()
                        .add<CurrencyConverterFragment>(R.id.fragmentContainer)
                        .commit()
                    true
                }

                else -> false
            }
        }

        if (savedInstanceState == null) {
            binding.bottomNavigation.selectedItemId = R.id.navCurrencyList
        }
    }
}