package com.marcelldr.thefolks.presentation.upgrade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityUpgradeP1Binding

class UpgradeP1Activity : AppCompatActivity() {
    private lateinit var binding: ActivityUpgradeP1Binding
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
            binding = ActivityUpgradeP1Binding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.upgradeP1BackButton.setOnClickListener { finish() }
            binding.upgradeP1Button.setOnClickListener {
                startActivity(Intent(this@UpgradeP1Activity, PhotoKtpActivity::class.java))
            }
        }

        statusAndActionBar()
        binding()
    }
}