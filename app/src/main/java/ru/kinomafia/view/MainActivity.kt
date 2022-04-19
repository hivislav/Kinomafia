package ru.kinomafia.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.kinomafia.R
import ru.kinomafia.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                    .replace(R.id.container, FilmInfoFragment.newInstance())
                    .commitNow()
        }
    }



}