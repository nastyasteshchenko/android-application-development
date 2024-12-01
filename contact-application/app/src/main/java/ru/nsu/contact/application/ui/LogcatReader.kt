package ru.nsu.contact.application.ui

import android.util.Log
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader

class LogcatReader(private var listener: LogcatListener) : Runnable {

    companion object {
        private const val TAG: String = "LogcatReader"
    }

    interface LogcatListener {
        fun onLogReceived(log: String?)
    }

    override fun run() {
        try {
            val pid = android.os.Process.myPid()
            val process =
                Runtime.getRuntime().exec("logcat --pid=$pid ru.nsu.contact.application")
            val bufferedReader = BufferedReader(
                InputStreamReader(process.inputStream)
            )

            var line: String?
            while ((bufferedReader.readLine().also { line = it }) != null) {
                listener.onLogReceived(line)
            }
        } catch (e: IOException) {
            Log.e(TAG, "Error reading logs", e)
        }
    }
}