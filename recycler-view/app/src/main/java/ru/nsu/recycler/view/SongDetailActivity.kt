package ru.nsu.recycler.view

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.facebook.drawee.view.SimpleDraweeView


class SongDetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.song_detail_activity)

        val songTitle = findViewById<TextView>(R.id.songTitle)
        val songAuthor = findViewById<TextView>(R.id.songAuthor)
        val songImage = findViewById<SimpleDraweeView>(R.id.songImage)
        val songDescription = findViewById<TextView>(R.id.songDescription)

        val title = intent.getStringExtra("title")
        val author = intent.getStringExtra("author")
        val imageUrl = intent.getStringExtra("imageUrl")
        val description = intent.getStringExtra("description")

        songTitle.text = title
        songAuthor.text = author
        songImage.setImageURI(imageUrl)
        songDescription.text = description
    }
}