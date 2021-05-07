package com.marcelldr.thefolksapp.presentation.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcelldr.thefolksapp.R

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        window.statusBarColor = resources.getColor(R.color.secondaryColor, this.theme)
        supportActionBar?.hide()
    }
}