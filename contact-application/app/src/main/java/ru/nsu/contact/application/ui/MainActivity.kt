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

    companion object {
        private const val VERTICAL_SPACE_HEIGHT_DP = 16
    }

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

    //TODO убрать моргание
    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Application).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        //Todo di
        val adapter = ContactAdapter {
            val intent = Intent(this, ShowContactInfoActivity::class.java)
            intent.putExtra("contact", it)
            startActivity(intent)
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        val spaceDecoration =
            SpaceItemDecoration(
                (VERTICAL_SPACE_HEIGHT_DP * resources.displayMetrics.density)
                    .toInt()
            )
        binding.recyclerView.addItemDecoration(spaceDecoration)

        viewModel.contacts.observe(this) {
            adapter.updateData(it)
        }

        viewModel.fetchContacts()

        binding.addContactButton.setOnClickListener {
            startActivity(Intent(this, AddContactActivity::class.java))
        }
    }
}