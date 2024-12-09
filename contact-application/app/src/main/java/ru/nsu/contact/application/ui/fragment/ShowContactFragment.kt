package ru.nsu.contact.application.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.R
import ru.nsu.contact.application.databinding.FragmentShowContactBinding
import ru.nsu.contact.application.domain.model.Contact

class ShowContactFragment : Fragment() {

    companion object {
        const val TAG: String = "ShowContact"
    }

    private val binding: FragmentShowContactBinding by lazy {
        FragmentShowContactBinding.inflate(
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

        setFragmentResultListener("showContact") { key, bundle ->
            val contact = bundle.getParcelable("contact", Contact::class.java)!!

            binding.nameTextView.text = contact.name
            binding.phoneTextView.text = contact.phoneNumber
            binding.contactImageView.setImageURI(contact.photoUri)

            binding.editButton.setOnClickListener {
                setFragmentResult("editContact", bundleOf("contact" to contact))
                requireActivity().supportFragmentManager.beginTransaction()
                    .add<EditContactFragment>(R.id.fragment_container, EditContactFragment.TAG)
                    .hide(this)
                    .addToBackStack(TAG)
                    .commit()
            }
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as Application).uiComponent.inject(this)
        super.onAttach(context)
    }
}