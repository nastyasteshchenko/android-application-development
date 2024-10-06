package ru.nsu.activity.activity.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityC : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_c)

        val toAButton = findViewById<Button>(R.id.toAButton)

        toAButton.setOnClickListener {
            val intent = Intent(this, ActivityA::class.java)
            startActivity(intent)
        }
    }
}