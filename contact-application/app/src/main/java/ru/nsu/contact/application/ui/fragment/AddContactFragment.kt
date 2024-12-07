package ru.nsu.contact.application.ui.fragment

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.databinding.FragmentAddContactBinding
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.nsu.contact.application.ui.copyImageToAppDirectory
import javax.inject.Inject

class AddContactFragment @Inject constructor() : Fragment() {

    companion object {
        const val TAG: String = "AddContact"
        private val DEFAULT_PHOTO_URI = Uri.parse("https://goo.su/03kflYr")
    }

    @Inject
    lateinit var viewModelFactory: ContactViewModel.ViewModelFactory

    private val viewModel: ContactViewModel by activityViewModels { viewModelFactory }

    private val binding: FragmentAddContactBinding by lazy {
        FragmentAddContactBinding.inflate(
            LayoutInflater.from(
                this.requireContext()
            )
        )
    }

    private var currentImageUri: Uri = DEFAULT_PHOTO_URI

    private val pickImageLauncher =
        registerForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
            uri?.let {
                binding.contactImage.setImageURI(uri)
                currentImageUri = uri
            }
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

        binding.contactImage.setImageURI(DEFAULT_PHOTO_URI)
        binding.contactImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        setTextWatcher()

        binding.saveButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val phone = binding.phoneEditText.text.toString()
            if (currentImageUri != DEFAULT_PHOTO_URI) {
                currentImageUri = copyImageToAppDirectory(this.requireContext(), currentImageUri)
            }
            if (name.isBlank() || phone.isBlank()) {
                Toast.makeText(this.requireContext(), "Fill all fields", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            val newContact = Contact(
                0, name, phone, currentImageUri.toString()
            )
            viewModel.addContact(newContact)
            requireActivity().supportFragmentManager
                .popBackStack(ContactListFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden) {
            binding.phoneEditText.text.clear()
            binding.nameEditText.text.clear()
            binding.contactImage.setImageURI(DEFAULT_PHOTO_URI)
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as Application).uiComponent.inject(this)
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
        workerThread.quit()
    }

    private fun setTextWatcher() {
        workerThread.start()
        val handler = Handler(workerThread.looper)
        handler.post {
            val phoneNumberFormattingTextWatcher = PhoneNumberFormattingTextWatcher("RU")
            binding.phoneEditText.addTextChangedListener(phoneNumberFormattingTextWatcher)
        }
    }
}