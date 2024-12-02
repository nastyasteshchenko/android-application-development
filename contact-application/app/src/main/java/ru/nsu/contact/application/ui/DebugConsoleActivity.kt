package ru.nsu.contact.application.ui

import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import ru.nsu.contact.application.databinding.ActivityDebugConsoleBinding

class DebugConsoleActivity : AppCompatActivity(), LogcatReader.LogcatListener {

    companion object {
        private const val MAX_LINES = 1000
    }

    private val logcatReader: LogcatReader = LogcatReader(this)
    private val logcatReaderThread: Thread = Thread(logcatReader)

    private val binding: ActivityDebugConsoleBinding by lazy {
        ActivityDebugConsoleBinding.inflate(
            LayoutInflater.from(this)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        binding.clearLogsButton.setOnClickListener {
            binding.logsTextView.text = ""
        }
        logcatReaderThread.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        logcatReader.stop()
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
