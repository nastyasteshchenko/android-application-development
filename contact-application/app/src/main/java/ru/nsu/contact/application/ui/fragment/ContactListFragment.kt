package ru.nsu.contact.application.ui.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.add
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import ru.nsu.contact.application.Application
import ru.nsu.contact.application.R
import ru.nsu.contact.application.databinding.FragmentContactListBinding
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.nsu.contact.application.ui.adapter.SpaceItemDecoration
import ru.nsu.contact.application.ui.adapter.ContactAdapter
import javax.inject.Inject

class ContactListFragment @Inject constructor() : Fragment() {

    companion object {
        const val TAG: String = "ContactList"
        private const val VERTICAL_SPACE_HEIGHT_DP = 16
    }

    @Inject
    lateinit var viewModelFactory: ContactViewModel.ViewModelFactory

    private val viewModel: ContactViewModel by activityViewModels { viewModelFactory }

    private val binding: FragmentContactListBinding by lazy {
        FragmentContactListBinding.inflate(
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

        val adapter = ContactAdapter {
            setFragmentResult("showContact", bundleOf("contact" to it))
            requireActivity().supportFragmentManager.beginTransaction()
                .add<ShowContactFragment>(R.id.fragment_container, ShowContactFragment.TAG)
                .addToBackStack(TAG)
                .commit()
        }

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(this.requireContext())
        val spaceDecoration =
            SpaceItemDecoration(
                (VERTICAL_SPACE_HEIGHT_DP * resources.displayMetrics.density)
                    .toInt()
            )
        binding.recyclerView.addItemDecoration(spaceDecoration)

        viewModel.contacts.observe(this.requireActivity()) {
            adapter.updateData(it)
        }

        viewModel.fetchContacts()

        binding.addContactButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add<AddContactFragment>(R.id.fragment_container, AddContactFragment.TAG)
                .addToBackStack(TAG)
                .commit()
        }
    }

    override fun onAttach(context: Context) {
        (requireActivity().application as Application).appComponent.inject(this)
        super.onAttach(context)
    }
}