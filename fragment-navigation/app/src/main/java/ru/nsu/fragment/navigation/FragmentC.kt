package ru.nsu.fragment.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentC : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_c, container, false)

        val toAButton: Button = view.findViewById(R.id.toAButton)
        toAButton.setOnClickListener {
            requireActivity().supportFragmentManager
                .popBackStack(Tag.FRAGMENT_A.tag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }

        return view
    }
}