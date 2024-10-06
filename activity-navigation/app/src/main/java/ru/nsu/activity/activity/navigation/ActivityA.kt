package ru.nsu.activity.activity.navigation

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class ActivityA : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_a)

        val toBButton = findViewById<Button>(R.id.toBButton)

        toBButton.setOnClickListener {
            val intent = Intent(this, ActivityB::class.java)
            startActivity(intent)
        }
    }
}