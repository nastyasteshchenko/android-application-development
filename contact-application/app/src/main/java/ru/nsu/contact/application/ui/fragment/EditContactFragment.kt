package ru.nsu.contact.application.ui.fragment

import android.app.AlertDialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.telephony.PhoneNumberFormattingTextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.R
import ru.nsu.contact.application.databinding.FragmentEditContactBinding
import ru.nsu.contact.application.domain.model.Contact
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.nsu.contact.application.ui.copyImageToAppDirectory
import javax.inject.Inject

class EditContactFragment @Inject constructor() : Fragment() {

    companion object {
        const val TAG: String = "EditContact"
        private val DEFAULT_PHOTO_URI = Uri.parse("https://goo.su/03kflYr")
    }

    @Inject
    lateinit var viewModelFactory: ContactViewModel.ViewModelFactory

    private val viewModel: ContactViewModel by activityViewModels { viewModelFactory }

    private val binding: FragmentEditContactBinding by lazy {
        FragmentEditContactBinding.inflate(
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

        binding.contactImage.setImageURI(currentImageUri)

        binding.contactImage.setOnClickListener {
            pickImageLauncher.launch("image/*")
        }

        binding.contactImage.setOnLongClickListener {
            showPopupMenu(it)
            true
        }

        setTextWatcher()

        setFragmentResultListener("editContact") { key, bundle ->
            val contact = bundle.getParcelable("contact", Contact::class.java)!!
            binding.nameEditText.setText(contact.name)
            binding.phoneEditText.setText(contact.phoneNumber)
            binding.contactImage.setImageURI(contact.photoUri)

            binding.saveButton.setOnClickListener {
                val name = binding.nameEditText.text.toString()
                val phone = binding.phoneEditText.text.toString()
                if (currentImageUri != DEFAULT_PHOTO_URI) {
                    currentImageUri =
                        copyImageToAppDirectory(this.requireContext(), currentImageUri)
                }
                if (name.isBlank() || phone.isBlank()) {
                    Toast.makeText(
                        this.requireContext(), "Fill all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                val newContact = Contact(
                    contact.id, name, phone, currentImageUri.toString()
                )
                viewModel.updateContact(newContact)
                setFragmentResult(
                    "showContact",
                    bundleOf("contact" to newContact)
                )
                requireActivity().supportFragmentManager
                    .popBackStack(ShowContactFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }

            binding.deleteButton.setOnClickListener {
                viewModel.deleteContact(contact)
                requireActivity().supportFragmentManager
                    .popBackStack(ContactListFragment.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
            }
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

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(requireContext(), view)
        popupMenu.menuInflater.inflate(R.menu.delete_option_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.delete_item -> {
                    showDeleteDialog()
                    true
                }

                else -> false
            }
        }
        popupMenu.show()
    }

    private fun showDeleteDialog() {
        AlertDialog.Builder(requireContext())
            .setMessage("Are you sure you want to delete this photo?")
            .setPositiveButton("Yes") { _, _ ->
                currentImageUri = DEFAULT_PHOTO_URI
                binding.contactImage.setImageURI(currentImageUri)
            }
            .setNegativeButton("No", null)
            .create()
            .show()
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