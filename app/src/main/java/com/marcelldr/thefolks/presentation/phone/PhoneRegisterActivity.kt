package com.marcelldr.thefolks.presentation.phone

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityPhoneRegisterBinding
import com.marcelldr.thefolks.presentation.dialog.LoadingDialog
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class PhoneRegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneRegisterBinding
    private var name: String = ""
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
            binding = ActivityPhoneRegisterBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.phoneRegisterBackButton.setOnClickListener { finish() }
            binding.phoneRegisterButton.setOnClickListener {
                submitForm()
            }
        }

        statusAndActionBar()
        binding()
    }

    private fun submitForm() {
        if(!validation()) {
            return
        }
        val intent = Intent(this@PhoneRegisterActivity, PhoneOtpActivity::class.java)
        intent.putExtra(PhoneOtpActivity.NAME, name)
        intent.putExtra(PhoneOtpActivity.NUMBER, number)
        intent.putExtra(PhoneOtpActivity.TYPE, "REGISTER")
        startActivity(intent)
    }

    private fun validation(): Boolean {
        val nameField = binding.phoneRegisterNameField.text.toString()
        val numberField = binding.phoneRegisterNumberField.text.toString()
        return if (nameField.isNotEmpty()) {
            name = nameField
            binding.phoneRegisterNameAlert.isErrorEnabled = false
            if (numberField.isNotEmpty()) {
                number = "+62" + numberField.substring(1)
                binding.phoneRegisterNameAlert.isErrorEnabled = false
                true
            } else {
                binding.phoneRegisterNumberAlert.error = "Phone number tidak boleh kosong!"
                binding.phoneRegisterNumberAlert.isErrorEnabled = true
                false
            }
        } else {
            binding.phoneRegisterNameAlert.error = "Nama lengkap tidak boleh kosong!"
            binding.phoneRegisterNameAlert.isErrorEnabled = true
            false
        }
    }


}