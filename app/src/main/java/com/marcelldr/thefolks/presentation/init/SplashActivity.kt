package com.marcelldr.thefolks.presentation.init

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.marcelldr.thefolks.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setUp()
    }

    private fun setUp() {
        fun statusAndActionBar() {
            window.statusBarColor = resources.getColor(R.color.grey, this.theme)
            supportActionBar?.hide()
        }

        statusAndActionBar()
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
            finish()
        }, 3000)
    }
}