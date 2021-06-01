package com.marcelldr.thefolks.presentation.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        fun statusAndActionBar() {
            window.statusBarColor = resources.getColor(R.color.grey, this.theme)
            supportActionBar?.hide()
        }

        fun bottomNavigation() {
            val hostFragment =
                supportFragmentManager.findFragmentById(R.id.homeHostFragment) as NavHostFragment
            binding.homeBottomNav.setupWithNavController(hostFragment.navController)

        }

        fun binding() {
            binding = ActivityHomeBinding.inflate(layoutInflater)
            setContentView(binding.root)
            bottomNavigation()
        }

        statusAndActionBar()
        binding()
    }
}