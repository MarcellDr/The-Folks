package com.marcelldr.thefolks.presentation.init

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityWelcomeBinding
import com.marcelldr.thefolks.presentation.access.LoginActivity
import com.marcelldr.thefolks.presentation.phone.PhoneActivity

class WelcomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWelcomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        fun statusAndActionBar() {
            window.statusBarColor = resources.getColor(R.color.secondary_color, this.theme)
            supportActionBar?.hide()
        }

        fun binding() {
            binding = ActivityWelcomeBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.welcomeButton.setOnClickListener {
//                startActivity(Intent(this@WelcomeActivity, LoginActivity::class.java))
                startActivity(Intent(this@WelcomeActivity, PhoneActivity::class.java))
                finish()
            }
        }

        statusAndActionBar()
        binding()
    }
}