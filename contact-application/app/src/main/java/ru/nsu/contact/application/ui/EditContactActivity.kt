package ru.nsu.contact.application.ui

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.databinding.ActivityEditContactBinding
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import javax.inject.Inject
import javax.inject.Singleton

class EditContactActivity : AppCompatActivity() {
    @Inject
    @Singleton
    lateinit var viewModelFactory: ContactViewModel.ViewModelFactory

    private val viewModel: ContactViewModel by viewModels { viewModelFactory }

    private val binding: ActivityEditContactBinding by lazy {
        ActivityEditContactBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Application).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        mask.isForbidInputWhenFilled = false
        val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)
        formatWatcher.installOn(binding.phoneEditText)

        val contact = intent.getSerializableExtra("contact", Contact::class.java)!!

        binding.nameEditText.setText(contact.name)
        binding.phoneEditText.setText(contact.phoneNumber)
        binding.contactImage.setImageURI(contact.photoUrl)

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            //TODO add photo
            if (name.isNotBlank() && phone.isNotBlank()) {
                val newContact = Contact(contact.id, name, phone, contact.photoUrl)
                viewModel.updateContact(newContact)
                val intent = Intent(this, ShowContactInfoActivity::class.java)
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                intent.putExtra("contact", newContact)
                startActivity(intent)
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }

        binding.deleteButton.setOnClickListener {
            viewModel.deleteContact(contact)
            val intent = Intent(this, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            startActivity(intent)
        }
    }
}