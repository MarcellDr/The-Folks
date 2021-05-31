package com.marcelldr.thefolks.presentation.phone

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityPhoneLoginBinding

class PhoneLoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneLoginBinding
    private var number: String = ""
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
            binding = ActivityPhoneLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.phoneLoginBackButton.setOnClickListener { finish() }
            binding.phoneLoginButton.setOnClickListener {
                submitForm()
            }
        }

        statusAndActionBar()
        binding()
    }

    private fun submitForm() {
        if (!validation()) {
            return
        }
        val intent = Intent(this@PhoneLoginActivity, PhoneOtpActivity::class.java)
        intent.putExtra(PhoneOtpActivity.NUMBER, number)
        intent.putExtra(PhoneOtpActivity.TYPE, "LOGIN")
        startActivity(intent)
    }

    private fun validation(): Boolean {
        val numberField = binding.phoneLoginNumberField.text.toString()
        return if (numberField.isNotEmpty()) {
            number = "+62" + numberField.substring(1)
            binding.phoneLoginNumberAlert.isErrorEnabled = false
            true
        } else {
            binding.phoneLoginNumberAlert.error = "Phone number tidak boleh kosong!"
            binding.phoneLoginNumberAlert.isErrorEnabled = true
            false
        }
    }
}