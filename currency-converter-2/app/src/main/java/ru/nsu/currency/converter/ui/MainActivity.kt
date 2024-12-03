package ru.nsu.currency.converter.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.currency.converter.Application
import ru.nsu.currency.converter.R
import ru.nsu.currency.converter.databinding.ActivityMainBinding
import ru.nsu.currency.converter.presentation.CurrencyViewModel
import ru.nsu.currency.converter.ui.fragment.CurrencyConverterFragment
import ru.nsu.currency.converter.ui.fragment.CurrencyListFragment
import javax.inject.Inject

//Todo  сделать чтобы огругление валюты было
class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: CurrencyViewModel.ViewModelFactory

    private val viewModel: CurrencyViewModel by viewModels { viewModelFactory }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Application).uiComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.navCurrencyList -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.main_container, CurrencyListFragment())
                        .commit()
                    true
                }

                R.id.navConverter -> {
                    supportFragmentManager.beginTransaction()
                        //TODO изменить переход по экранам
                        .replace(R.id.main_container, CurrencyConverterFragment())
                        .commit()
                    true
                }

                else -> false
            }
        }

        binding.bottomNavigation.selectedItemId = R.id.navCurrencyList

    }
}