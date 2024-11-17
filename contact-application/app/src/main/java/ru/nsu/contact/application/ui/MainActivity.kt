package ru.nsu.contact.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.R
import ru.nsu.contact.application.databinding.ActivityMainBinding
import ru.nsu.contact.application.presentation.ContactViewModel
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var editContactFragment: EditContactFragment

    @Inject
    lateinit var addContactFragment: AddContactFragment

    @Inject
    lateinit var showContactFragment: ShowContactFragment

    @Inject
    lateinit var contactListFragment: ContactListFragment

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
        (applicationContext as Application).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, contactListFragment, ContactListFragment.TAG)
            .add(R.id.fragment_container, editContactFragment, EditContactFragment.TAG)
            .add(R.id.fragment_container, showContactFragment, ShowContactFragment.TAG)
            .add(R.id.fragment_container, addContactFragment, AddContactFragment.TAG)
            .hide(addContactFragment)
            .hide(editContactFragment)
            .hide(showContactFragment)
            .commit()
    }
}