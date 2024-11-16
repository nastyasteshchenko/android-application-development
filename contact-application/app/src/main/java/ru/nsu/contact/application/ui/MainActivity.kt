package ru.nsu.contact.application.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.databinding.ActivityMainBinding
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.nsu.contact.application.ui.adapter.ContactAdapter
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

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Application).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val adapter = ContactAdapter()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel.contacts.observe(this) { contacts ->
            adapter.updateContacts(contacts)
        }

        viewModel.fetchContacts()

        Log.e(null, binding.addContactButton.text.toString())
        binding.addContactButton.setOnClickListener {
            startActivity(Intent(this, AddContactActivity::class.java))
        }
    }

}