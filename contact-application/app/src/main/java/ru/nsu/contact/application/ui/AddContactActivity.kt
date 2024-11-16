package ru.nsu.contact.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.databinding.ActivityAddContactBinding
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import javax.inject.Inject
import javax.inject.Singleton


class AddContactActivity : AppCompatActivity() {

    @Inject
    @Singleton
    lateinit var viewModelFactory: ContactViewModel.ViewModelFactory

    //TODO singleton for view model or some other fixes
    private val viewModel: ContactViewModel by viewModels { viewModelFactory }

    private val binding: ActivityAddContactBinding by lazy {
        ActivityAddContactBinding.inflate(
            LayoutInflater.from(
                this
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        (application as Application).appComponent.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.contactImage.setImageURI("https://goo.su/03kflYr")

        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        mask.isForbidInputWhenFilled = false
        val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)
        formatWatcher.installOn(binding.phoneEditText)

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            //TODO add photo
            if (name.isNotBlank() && phone.isNotBlank()) {
                val newContact = Contact(
                    0, name, phone,
                    "https://goo.su/03kflYr"
                )
                viewModel.addContact(newContact)
                finish()
            } else {
                Toast.makeText(this, "Fill all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}