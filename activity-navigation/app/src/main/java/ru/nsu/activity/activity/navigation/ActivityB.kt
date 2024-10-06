package ru.nsu.activity.activity.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityB : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_b)

        val toCButton = findViewById<Button>(R.id.toCButton)

        toCButton.setOnClickListener {
            val intent = Intent(this, ActivityC::class.java)
            startActivity(intent)
        }
    }
}