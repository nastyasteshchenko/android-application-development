package ru.nsu.contact.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import ru.nsu.contact.application.databinding.FragmentEditContactBinding
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.FormatWatcher
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import javax.inject.Inject
import javax.inject.Singleton

class EditContactFragment @Inject constructor(): Fragment() {

    companion object {
        const val TAG: String = "EditContact"
    }

    @Inject
    @Singleton
    lateinit var viewModelFactory: ContactViewModel.ViewModelFactory

    private val viewModel: ContactViewModel by activityViewModels{viewModelFactory}

    private val binding: FragmentEditContactBinding by lazy {
        FragmentEditContactBinding.inflate(
            LayoutInflater.from(
                this.requireContext()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mask = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        mask.isForbidInputWhenFilled = false
        val formatWatcher: FormatWatcher = MaskFormatWatcher(mask)
        formatWatcher.installOn(binding.phoneEditText)

        setFragmentResultListener("editContact") { key, bundle ->
            val contact = bundle.getParcelable("contact", Contact::class.java)!!
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
                    setFragmentResult("showContact", bundleOf("contact" to newContact))
                    requireActivity().supportFragmentManager
                        .popBackStack(
                            ShowContactFragment.TAG,
                            FragmentManager.POP_BACK_STACK_INCLUSIVE
                        )
                } else {
                    Toast.makeText(this.requireContext(), "Fill all fields", Toast.LENGTH_SHORT)
                        .show()
                }
            }

            binding.deleteButton.setOnClickListener {
                viewModel.deleteContact(contact)
                requireActivity().supportFragmentManager
                    .popBackStack(ContactListFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
        }
    }
}