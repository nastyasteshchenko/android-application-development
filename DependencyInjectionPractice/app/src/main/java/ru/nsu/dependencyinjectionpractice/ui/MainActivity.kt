package ru.nsu.dependencyinjectionpractice.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

import ru.nsu.dependencyinjectionpractice.HomeworkApplication
import ru.nsu.dependencyinjectionpractice.R
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainFragment: MainFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as HomeworkApplication).appComponent.inject(this)

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, mainFragment)
                .commit()
        }
    }
}