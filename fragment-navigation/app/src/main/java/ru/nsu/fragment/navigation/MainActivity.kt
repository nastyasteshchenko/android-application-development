package ru.nsu.fragment.navigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val fragmentA = FragmentA()
        val fragmentB = FragmentB()
        val fragmentC = FragmentC()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, fragmentA)
            .add(R.id.fragment_container, fragmentB, Tag.FRAGMENT_B.tag )
            .add(R.id.fragment_container, fragmentC, Tag.FRAGMENT_C.tag)
            .hide(fragmentB)
            .hide(fragmentC)
            .commit()
    }
}