package ru.nsu.contact.application.ui

import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import ru.nsu.contact.application.databinding.FragmentAddContactBinding
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.presentation.ContactViewModel
import javax.inject.Inject
import javax.inject.Singleton

class AddContactFragment @Inject constructor() : Fragment() {

    companion object {
        const val TAG: String = "AddContact"
    }

    @Inject
    @Singleton
    lateinit var viewModelFactory: ContactViewModel.ViewModelFactory

    private val viewModel: ContactViewModel by activityViewModels { viewModelFactory }

    private val binding: FragmentAddContactBinding by lazy {
        FragmentAddContactBinding.inflate(
            LayoutInflater.from(
                this.requireContext()
            )
        )
    }

    private val workerThread = HandlerThread("WorkerThread")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.contactImage.setImageURI("https://goo.su/03kflYr")

        workerThread.start()
        val handler = Handler(workerThread.looper)
        handler.post {
            val phoneNumberFormattingTextWatcher = PhoneNumberFormattingTextWatcher("RU")
            binding.phoneEditText.addTextChangedListener(phoneNumberFormattingTextWatcher)
        }

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            //TODO add photo
            if (name.isBlank() || phone.isBlank()) {
                Toast.makeText(this.requireContext(), "Fill all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val newContact = Contact(
                0, name, phone,
                "https://goo.su/03kflYr"
            )
            viewModel.addContact(newContact)
            requireActivity().supportFragmentManager
                .popBackStack(ContactListFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        workerThread.quit()
    }
}