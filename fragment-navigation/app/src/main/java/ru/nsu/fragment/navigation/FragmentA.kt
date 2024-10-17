package ru.nsu.fragment.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentA : Fragment() {

    companion object {
        const val TAG: String = "A"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_a, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toBButton: Button = view.findViewById(R.id.toBButton)

        val fragmentB =
            requireActivity().supportFragmentManager.findFragmentByTag(FragmentB.TAG)!!

        toBButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .hide(this)
                .show(fragmentB)
                .addToBackStack(TAG)
                .commit()
        }
    }
}