package com.marcelldr.thefolks.presentation.access

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityRegisterBinding
import com.marcelldr.thefolks.presentation.dialog.LoadingDialog
import com.marcelldr.thefolks.presentation.dialog.SuccessDialog
import org.koin.android.ext.android.inject

class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val mAuth: FirebaseAuth by inject()
    private val db: FirebaseFirestore by inject()
    private var email: String = ""
    private var password: String = ""
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
            binding = ActivityRegisterBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.registerBackButton.setOnClickListener { finish() }
            binding.registerButton.setOnClickListener { submitForm() }
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
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                registerUser()
                loadingDialog.dismiss()
                val registerSuccessDialog = SuccessDialog(
                    this,
                    "Congratulations, your account has been successfully created"
                )
                registerSuccessDialog.show()
            } else {
                loadingDialog.dismiss()
                Toast.makeText(
                    baseContext, "Something went wrong",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun registerUser() {
        val user = mAuth.currentUser
        val data = HashMap<String, Any>()
        data["uid"] = user?.uid.toString()
        data["email"] = user?.email.toString()
        db.collection("users")
            .document(user?.uid.toString())
            .set(data)
    }

    private fun validation(): Boolean {
        val emailField = binding.registerEmailField.text.toString()
        val passwordField = binding.registerPasswordField.text.toString()
        return if (emailField.isNotEmpty()) {
            email = emailField
            binding.registerEmailAlert.isErrorEnabled = false
            if (passwordField.isNotEmpty()) {
                password = passwordField
                binding.registerPasswordAlert.isErrorEnabled = false
                true
            } else {
                binding.registerPasswordAlert.error = "Password tidak boleh kosong!"
                binding.registerPasswordAlert.isErrorEnabled = true
                false
            }
        } else {
            binding.registerEmailAlert.error = "Email tidak boleh kosong!"
            binding.registerEmailAlert.isErrorEnabled = true
            false
        }
    }
}