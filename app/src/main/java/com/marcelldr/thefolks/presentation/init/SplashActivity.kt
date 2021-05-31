package com.marcelldr.thefolks.presentation.init

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.presentation.home.HomeActivity
import org.koin.android.ext.android.inject

class SplashActivity : AppCompatActivity() {
    private val mAuth: FirebaseAuth by inject()
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
            if(mAuth.currentUser == null) {
                startActivity(Intent(this@SplashActivity, WelcomeActivity::class.java))
                finish()
            } else {
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }

        }, 3000)
    }
}