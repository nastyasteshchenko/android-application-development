package ru.nsu.recycler.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.facebook.drawee.backends.pipeline.Fresco
import ru.nsu.recycler.view.adapter.PlayerAdapter

class MainActivity : AppCompatActivity() {

    companion object {
        private const val VERTICAL_SPACE_HEIGHT_DP = 16
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Fresco.initialize(this)

        val adapter = PlayerAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val spaceDecoration =
            SpaceItemDecoration(
                (VERTICAL_SPACE_HEIGHT_DP * resources.displayMetrics.density)
                    .toInt()
            )
        recyclerView.addItemDecoration(spaceDecoration)

        val test = Test()
        test.startTest(adapter)
    }
}