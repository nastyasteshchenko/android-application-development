package ru.nsu.contact.application.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.databinding.ActivityShowContactBinding
import ru.nsu.contact.application.domain.model.Contact
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

class ShowContactInfoActivity : AppCompatActivity() {

    private val binding: ActivityShowContactBinding by lazy {
        ActivityShowContactBinding.inflate(
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
        formatWatcher.installOn(binding.phoneTextView)

        val contact = intent.getSerializableExtra("contact", Contact::class.java)!!

        binding.nameTextView.text = contact.name
        binding.phoneTextView.text = contact.phoneNumber
        binding.contactImageView.setImageURI(contact.photoUrl)

        binding.editButton.setOnClickListener {
            val intent = Intent(this, EditContactActivity::class.java)
            intent.putExtra("contact", contact)
            startActivity(intent)
        }
    }
}