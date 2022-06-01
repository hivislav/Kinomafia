package ru.kinomafia.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.main_activity.*
import pl.droidsonroids.gif.GifDrawable
import ru.kinomafia.R
import ru.kinomafia.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val gifDrawable = GifDrawable(resources, R.drawable.start_app_animation)
        startAppAnimation.setImageDrawable(gifDrawable)
        gifDrawable.loopCount = 1

        gifDrawable.addAnimationListener {
            startAppAnimation.hide()
            if (savedInstanceState == null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container, MainFragment.newInstance())
                    .commitNow()
            }
        }
    }
}