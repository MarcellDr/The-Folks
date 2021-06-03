package com.marcelldr.thefolks.presentation.upgrade

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityUpgradeP2Binding

class UpgradeP2Activity : AppCompatActivity() {
    private lateinit var binding: ActivityUpgradeP2Binding
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
            binding = ActivityUpgradeP2Binding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.upgradeP2BackButton.setOnClickListener { finish() }
            binding.upgradeP2Button.setOnClickListener {
                startActivity(Intent(this@UpgradeP2Activity, PhotoSelfieActivity::class.java))
            }
        }

        statusAndActionBar()
        binding()
    }
}