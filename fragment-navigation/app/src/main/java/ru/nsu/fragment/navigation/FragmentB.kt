package ru.nsu.fragment.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FragmentB : Fragment() {

    companion object {
        const val TAG: String = "B"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_b, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val fragmentC =
            requireActivity().supportFragmentManager.findFragmentByTag(FragmentC.TAG)!!

        val toCButton: Button = view.findViewById(R.id.toCButton)
        toCButton.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .hide(this)
                .show(fragmentC)
                .addToBackStack(null)
                .commit()
        }
    }
}