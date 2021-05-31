package com.marcelldr.thefolks.presentation.phone

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityPhoneBinding

class PhoneActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        fun statusAndActionBar() {
            window.statusBarColor = resources.getColor(R.color.grey, this.theme)
            supportActionBar?.hide()
        }

        fun binding() {
            binding = ActivityPhoneBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.phoneLoginButton.setOnClickListener {
                startActivity(Intent(this@PhoneActivity, PhoneLoginActivity::class.java))
            }
            binding.phoneRegisterButton.setOnClickListener {
                startActivity(Intent(this@PhoneActivity, PhoneRegisterActivity::class.java))
            }
        }

        statusAndActionBar()
        binding()
    }
}