package ru.nsu.contact.application.ui

import android.Manifest
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

    @Inject
    lateinit var viewModelFactory: ContactViewModel.ViewModelFactory

    private val viewModel: ContactViewModel by viewModels { viewModelFactory }

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            viewModel.fetchContactsFromContactBook()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as Application).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fetchContactsFromPhone()

        supportFragmentManager.beginTransaction()
            .add<ContactListFragment>(R.id.fragment_container, ContactListFragment.TAG)
            .commit()
    }

    private fun fetchContactsFromPhone() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }
}