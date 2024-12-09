package ru.nsu.contact.application.ui

import android.Manifest
import ru.nsu.contact.application.BuildConfig
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.add
import com.squareup.seismic.ShakeDetector
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.R
import ru.nsu.contact.application.databinding.ActivityMainBinding
import ru.nsu.contact.application.presentation.MainViewModel
import ru.nsu.contact.application.ui.fragment.ContactListFragment
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    companion object {
        private const val APP_PREFERENCES_NAME = "app_preferences"
        private const val IS_PERMISSION_REQUESTED_PREFERENCES_NAME = "is_permission_requested"
        private var isDebugConsoleActive = false
    }

    @Inject
    lateinit var viewModelFactory: MainViewModel.ViewModelFactory

    private val viewModel: MainViewModel by viewModels { viewModelFactory }

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

        if (BuildConfig.ENABLE_DEBUG_CONSOLE) {
            val shakeDetector =
                ShakeDetector {
                    if (!isDebugConsoleActive) {
                        isDebugConsoleActive = true
                        startActivity(Intent(this, DebugConsoleActivity::class.java))
                    }
                }
            shakeDetector
                .start((getSystemService(SENSOR_SERVICE) as android.hardware.SensorManager))
        }

        supportFragmentManager.beginTransaction()
            .add<ContactListFragment>(R.id.fragment_container, ContactListFragment.TAG)
            .commit()
    }

    override fun onResume() {
        super.onResume()
        isDebugConsoleActive = false
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
