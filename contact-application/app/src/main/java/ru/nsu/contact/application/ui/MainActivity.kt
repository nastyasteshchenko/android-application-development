package ru.nsu.contact.application.ui

import android.Manifest
import android.content.pm.PackageManager
import android.database.Cursor
import android.os.Bundle
import android.provider.ContactsContract
import android.view.LayoutInflater
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.R
import ru.nsu.contact.application.databinding.ActivityMainBinding
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.nsu.contact.application.ui.fragment.AddContactFragment
import ru.nsu.contact.application.ui.fragment.ContactListFragment
import ru.nsu.contact.application.ui.fragment.EditContactFragment
import ru.nsu.contact.application.ui.fragment.ShowContactFragment
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

    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { result ->
        if (result) {
            loadContacts()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as Application).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        fetchContactsFromPhone()

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

    private fun fetchContactsFromPhone() {
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_CONTACTS
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.READ_CONTACTS)
        }
    }

    private fun loadContacts() {
        val contacts = mutableListOf<Contact>()
        val cursor: Cursor? = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.PHOTO_URI
            ),
            null, null, null
        )

        cursor?.use {
            while (it.moveToNext()) {
                val id =
                    it.getString(
                        it.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID
                        )
                    )
                val name =
                    it.getString(
                        it.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
                        )
                    )
                val phoneNumber =
                    it.getString(
                        it.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.NUMBER
                        )
                    )
                var photo =
                    it.getString(
                        it.getColumnIndexOrThrow(
                            ContactsContract.CommonDataKinds.Phone.PHOTO_URI
                        )
                    )
                if (photo.isNullOrEmpty()) {
                    photo = "https://goo.su/03kflYr"
                }
                val contact = Contact(
                    id.toLong(), name = name, phoneNumber = phoneNumber,
                    photoUri = photo
                )

                if (!contacts.contains(contact)) {
                    contacts.add(contact)
                }
            }
        }

        viewModel.addContacts(contacts)
    }
}