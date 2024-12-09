package ru.nsu.contact.application.ui

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.R
import ru.nsu.contact.application.databinding.ActivityMainBinding
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.nsu.contact.application.ui.fragment.ContactListFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val APP_PREFERENCES_NAME = "app_preferences"
        private const val IS_PERMISSION_REQUESTED_PREFERENCES_NAME = "is_permission_requested"
    }

    @Inject
    lateinit var viewModelFactory: ContactViewModel.ViewModelFactory

    private val viewModel: ContactViewModel by viewModels { viewModelFactory }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            viewModel.fetchContactsFromContactBook()
        }
        savePermissionStatus()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Application).uiComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fetchContactsFromPhone()

        supportFragmentManager.beginTransaction()
            .add<ContactListFragment>(R.id.fragment_container, ContactListFragment.TAG)
            .commit()
    }

    private fun fetchContactsFromPhone() {
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)
        val isPermissionRequested = sharedPreferences.getBoolean(
            IS_PERMISSION_REQUESTED_PREFERENCES_NAME, false
        )

        if (!isPermissionRequested) {
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.READ_CONTACTS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
            }
        }
    }

    private fun savePermissionStatus() {
        val sharedPreferences = getSharedPreferences(APP_PREFERENCES_NAME, Context.MODE_PRIVATE)
        with(sharedPreferences.edit()) {
            putBoolean(IS_PERMISSION_REQUESTED_PREFERENCES_NAME, true)
            apply()
        }
    }
}
