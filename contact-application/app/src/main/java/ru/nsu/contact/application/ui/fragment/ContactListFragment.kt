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
import ru.nsu.contact.application.BuildConfig
import ru.nsu.contact.application.R
import ru.nsu.contact.application.databinding.FragmentContactListBinding
import ru.nsu.contact.application.presentation.ContactViewModel
import ru.nsu.contact.application.ui.adapter.SpaceItemDecoration
import ru.nsu.contact.application.ui.adapter.ContactAdapter
import ru.nsu.contact.application.ui.mapper.BannerItemMapper
import ru.nsu.contact.application.ui.mapper.ContactItemMapper
import javax.inject.Inject

class ContactListFragment @Inject constructor() : Fragment() {

    companion object {
        const val TAG: String = "ContactList"
        private const val VERTICAL_SPACE_HEIGHT_DP = 16
    }

    @Inject
    lateinit var contactItemMapper: ContactItemMapper

    @Inject
    lateinit var bannerItemMapper: BannerItemMapper

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
            val contact = contactItemMapper.contactItemToContact(it)
            setFragmentResult("showContact", bundleOf("contact" to contact))
            requireActivity().supportFragmentManager.beginTransaction()
                .add<ShowContactFragment>(R.id.fragment_container, ShowContactFragment.TAG)
                .hide(this)
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
            val newItems = ArrayList(contactItemMapper.contactToContactItem(it))
            if (BuildConfig.IS_FREE) {
                val banner = bannerItemMapper.bannerToBannerItem(viewModel.getBanner())
                if (newItems.size < 2) {
                    newItems.add(banner)
                } else {
                    newItems.add(1, banner)
                }
            }
            adapter.updateData(newItems)
        }

        viewModel.fetchContacts()

        binding.addContactButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .add<AddContactFragment>(R.id.fragment_container, AddContactFragment.TAG)
                .hide(this)
                .addToBackStack(TAG)
                .commit()
        }
    }


    override fun onAttach(context: Context) {
        (requireActivity().application as Application).uiComponent.inject(this)
        super.onAttach(context)
    }
}