package ru.nsu.fragment.navigation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

class FragmentC : Fragment() {

    companion object {
        const val TAG: String = "C"
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_c, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val toAButton: Button = view.findViewById(R.id.toAButton)
        toAButton.setOnClickListener {
            requireActivity().supportFragmentManager
                .popBackStack(FragmentA.TAG, FragmentManager.POP_BACK_STACK_INCLUSIVE)
        }
    }
}