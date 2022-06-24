package ru.kinomafia.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.main_activity.*
import pl.droidsonroids.gif.GifDrawable
import ru.kinomafia.R
import ru.kinomafia.databinding.MainActivityBinding
import ru.kinomafia.view.favourite.FavouriteFragment
import ru.kinomafia.view.main.MainFragment

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = MainActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.mainBottomNavigation.hide()


        val gifDrawable = GifDrawable(resources, R.drawable.start_app_animation)
        startAppAnimation.setImageDrawable(gifDrawable)
        gifDrawable.loopCount = 1

        gifDrawable.addAnimationListener {
            startAppAnimation.hide()
            binding.mainBottomNavigation.show()
            if (savedInstanceState == null) {
                binding.mainBottomNavigation.selectedItemId = R.id.itemBottomNavigationSearch
            }
        }

        binding.mainBottomNavigation.setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.itemBottomNavigationNews -> {
                    true
                }

                R.id.itemBottomNavigationSearch -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MainFragment.newInstance())
                        .commitNow()
                    true
                }

                R.id.itemBottomNavigationFavourite -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, FavouriteFragment.newInstance())
                        .commitAllowingStateLoss()
                    true
                }

                R.id.itemBottomNavigationSettings -> {
                    true
                }
                else -> {
                    false
                }
            }
        }
    }
}