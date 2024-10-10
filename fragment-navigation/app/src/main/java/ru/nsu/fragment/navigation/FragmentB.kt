package ru.nsu.fragment.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentB : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_b, container, false)

        val fragmentC =
            requireActivity().supportFragmentManager.findFragmentByTag(Tag.FRAGMENT_C.tag)!!

        val toCButton: Button = view.findViewById(R.id.toCButton)
        toCButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .hide(this)
                .show(fragmentC)
                .addToBackStack(null)
                .commit()
        }

        return view
    }
}