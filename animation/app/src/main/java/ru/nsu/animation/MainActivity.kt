package ru.nsu.animation

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var dampedVibrationAnimator: DampedVibrationAnimator
    private lateinit var editText: EditText
    private lateinit var submitButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        submitButton = findViewById(R.id.submitButton)

        dampedVibrationAnimator = DampedVibrationAnimator(editText)

        submitButton.setOnClickListener {
            validateInput()
        }
    }

    private fun validateInput() {
        val input = editText.text.toString()
        if (input.isEmpty()) {
            dampedVibrationAnimator.start()
        } else {
            Toast.makeText(this, ":)", Toast.LENGTH_SHORT).show()
        }
    }
}