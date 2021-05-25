package com.marcelldr.thefolks.presentation.access

import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityForgetBinding
import com.marcelldr.thefolks.presentation.dialog.ForgetSuccessDialog
import com.marcelldr.thefolks.presentation.dialog.LoadingDialog
import org.koin.android.ext.android.inject

class ForgetActivity : AppCompatActivity() {
    private lateinit var binding: ActivityForgetBinding
    private val mAuth: FirebaseAuth by inject()
    private var email: String = ""
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
            binding = ActivityForgetBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.forgetBackButton.setOnClickListener { finish() }
            binding.forgetSendButton.setOnClickListener { submitForm() }
        }

        statusAndActionBar()
        binding()
    }

    private fun submitForm() {
        if (!validation()) {
            return
        }
        val loadingDialog = LoadingDialog(this)
        loadingDialog.show()
        mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
            if (it.isSuccessful) {
                loadingDialog.dismiss()
                val forgetSuccessDialog = ForgetSuccessDialog(this)
                forgetSuccessDialog.show()
            } else {
                loadingDialog.dismiss()
                Log.w(ContentValues.TAG, "forgetPassword:failure", it.exception)
                Toast.makeText(
                    baseContext, "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun validation(): Boolean {
        val emailField = binding.forgetEmailField.text.toString()
        return if (emailField.isNotEmpty()) {
            email = emailField
            binding.forgetEmailAlert.isErrorEnabled = false
            true
        } else {
            binding.forgetEmailAlert.error = "Email tidak boleh kosong"
            binding.forgetEmailAlert.isErrorEnabled = true
            false
        }
    }
}