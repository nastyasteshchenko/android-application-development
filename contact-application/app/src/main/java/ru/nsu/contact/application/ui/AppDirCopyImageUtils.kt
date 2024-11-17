package ru.nsu.contact.application.ui

import android.content.Context
import android.net.Uri
import java.io.File
import java.io.FileOutputStream

fun copyImageToAppDirectory(context: Context, uri: Uri): Uri {
    val inputStream = context.contentResolver.openInputStream(uri)
    val tempFile = File(context.filesDir, "image_${System.currentTimeMillis()}.jpg")
    val outputStream = FileOutputStream(tempFile)

    inputStream?.use { input ->
        outputStream.use { output ->
            input.copyTo(output)
        }
    }

    return Uri.fromFile(tempFile)
}
