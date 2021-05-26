package com.marcelldr.thefolks.presentation.access

import android.content.ContentValues.TAG
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityLoginBinding
import com.marcelldr.thefolks.presentation.dialog.LoadingDialog
import com.marcelldr.thefolks.presentation.upgrade.UpgradeP1Activity
import org.koin.android.ext.android.inject

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private val mAuth: FirebaseAuth by inject()
    private var email: String = ""
    private var password: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        fun statusAndActionBar() {
            window?.decorView?.systemUiVisibility = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
            window?.statusBarColor = Color.TRANSPARENT
            supportActionBar?.hide()
        }

        fun binding() {
            binding = ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.forgetPasswordButton.setOnClickListener {
                startActivity(Intent(this@LoginActivity, ForgetActivity::class.java))
            }
            binding.signUpButton.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
            binding.loginButton.setOnClickListener { submitForm() }
        }

        statusAndActionBar()
        binding()
    }

    private fun submitForm() {
        if(!validation()) {
            return
        }
        val loadingDialog = LoadingDialog(this)
        loadingDialog.show()
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            if(it.isSuccessful) {
                loadingDialog.dismiss()
                Toast.makeText(baseContext, "Login berhasil",
                    Toast.LENGTH_SHORT).show()
                startActivity(Intent(this@LoginActivity, UpgradeP1Activity::class.java))
                finish()
            } else {
                loadingDialog.dismiss()
                Toast.makeText(baseContext, resources.getString(R.string.error_message),
                    Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun validation(): Boolean {
        val emailField = binding.loginEmailField.text.toString()
        val passwordField = binding.loginPasswordField.text.toString()
        return if(emailField.isNotEmpty()) {
            email = emailField
            binding.loginEmailAlert.isErrorEnabled = false
            if(passwordField.isNotEmpty()) {
                password = passwordField
                binding.loginPasswordAlert.isErrorEnabled = false
                true
            } else {
                binding.loginPasswordAlert.error = "Password tidak boleh kosong!"
                binding.loginPasswordAlert.isErrorEnabled = true
                false
            }
        } else {
            binding.loginEmailAlert.error = "Email tidak boleh kosong!"
            binding.loginEmailAlert.isErrorEnabled = true
            false
        }
    }
}