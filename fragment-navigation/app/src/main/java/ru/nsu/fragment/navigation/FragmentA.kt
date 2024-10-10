package ru.nsu.fragment.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentA : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_a, container, false)

        val toBButton: Button = view.findViewById(R.id.toBButton)

        val fragmentB =
            requireActivity().supportFragmentManager.findFragmentByTag(Tag.FRAGMENT_B.tag)!!

        toBButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .hide(this)
                .show(fragmentB)
                .addToBackStack(Tag.FRAGMENT_A.tag)
                .commit()
        }

        return view
    }
}