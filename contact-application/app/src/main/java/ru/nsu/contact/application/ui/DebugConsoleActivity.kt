package ru.nsu.contact.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.contact.application.databinding.ActivityDebugConsoleBinding

class DebugConsoleActivity : AppCompatActivity(), LogcatReader.LogcatListener {

    companion object {
        private const val MAX_LINES = 1000
    }

    private val binding: ActivityDebugConsoleBinding by lazy {
        ActivityDebugConsoleBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val logcatReader = LogcatReader(this)
        Thread(logcatReader).start()
    }

    override fun onLogReceived(log: String?) {
        runOnUiThread {
            val currentLogs = binding.logsTextView.text.toString().lines()
                .takeLast(MAX_LINES - 1)
            val updatedLogs = (currentLogs + log).joinToString(System.lineSeparator())
            binding.logsTextView.text = updatedLogs
        }
    }
}
