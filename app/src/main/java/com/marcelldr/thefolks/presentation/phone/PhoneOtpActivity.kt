package com.marcelldr.thefolks.presentation.phone

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.FirebaseException
import com.google.firebase.FirebaseTooManyRequestsException
import com.google.firebase.auth.*
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.messaging.FirebaseMessaging
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityPhoneOtpBinding
import com.marcelldr.thefolks.presentation.dialog.LoadingDialog
import com.marcelldr.thefolks.presentation.dialog.SuccessDialog
import com.marcelldr.thefolks.presentation.home.HomeActivity
import org.koin.android.ext.android.inject
import java.util.concurrent.TimeUnit

class PhoneOtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhoneOtpBinding
    private val mAuth: FirebaseAuth by inject()
    private val db: FirebaseFirestore by inject()
    private var name: String? = ""
    private var number: String = ""
    private var type: String = ""
    private var code: String? = null
    private var storedVerificationId: String? = null
    private lateinit var loadingDialog: LoadingDialog
    private lateinit var callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks

    companion object {
        const val NAME = "name"
        const val NUMBER = "number"
        const val TYPE = "type"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        name = intent.getStringExtra(NAME)
        number = intent.getStringExtra(NUMBER) as String
        type = intent.getStringExtra(TYPE) as String

        fun statusAndActionBar() {
            window.statusBarColor = resources.getColor(R.color.grey, this.theme)
            supportActionBar?.hide()
        }

        fun binding() {
            binding = ActivityPhoneOtpBinding.inflate(layoutInflater)
            setContentView(binding.root)

            binding.phoneOtpNumber.text = number
            binding.phoneOtpBackButton.setOnClickListener { finish() }
            binding.phoneOtpButton.setOnClickListener {
                loadingDialog.show()
                val cred = PhoneAuthProvider.getCredential(storedVerificationId!!, code!!)
                mAuth.signInWithCredential(cred).addOnCompleteListener {
                    if (it.isSuccessful) {
                        loadingDialog.dismiss()
                        val successDialog = if (type == "REGISTER") {
                            registerData()
                            setToken()
                            SuccessDialog(this, "Registrasi berhasil!")
                        } else {
                            setToken()
                            SuccessDialog(this, "Login berhasil!")
                        }
                        successDialog.show()
                        successDialog.setOnContinueClickCallback(object :
                            SuccessDialog.OnContinueClickCallback {
                            override fun onClicked() {
                                successDialog.dismiss()
                                val intent = Intent(this@PhoneOtpActivity, HomeActivity::class.java)
                                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(intent)
                            }
                        })
                    } else {
                        loadingDialog.dismiss()
                        Toast.makeText(
                            baseContext, resources.getString(R.string.error_message),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }
        }

        statusAndActionBar()
        binding()

        loadingDialog = LoadingDialog(this)
        loadingDialog.show()
        startPhoneNumberVerification(number)
    }

    private fun registerData() {
        val user = mAuth.currentUser
        val data = HashMap<String, Any>()
        data["uid"] = user?.uid.toString()
        data["name"] = name!!
        data["number"] = number
        data["status"] = "guest"
        db.collection("users")
            .document(user?.uid.toString())
            .set(data)
    }

    private fun setToken() {
        val user = mAuth.currentUser
        val data = HashMap<String, Any>()
        FirebaseMessaging.getInstance().token.addOnCompleteListener {
            val token = it.result
            data["token"] = token.toString()
            db.collection("users")
                .document(user?.uid.toString())
                .update(data)
        }
    }

    private fun callback() {
        callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeAutoRetrievalTimeOut(p0: String) {
                super.onCodeAutoRetrievalTimeOut(p0)
                loadingDialog.dismiss()
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                code = p0.smsCode
                loadingDialog.dismiss()
                if (code != null) {
                    binding.otpView.setText(code)
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                if (p0 is FirebaseAuthInvalidCredentialsException) {
                    // Invalid request
                    loadingDialog.dismiss()
                    Toast.makeText(
                        baseContext, resources.getString(R.string.error_message),
                        Toast.LENGTH_SHORT
                    ).show()
                } else if (p0 is FirebaseTooManyRequestsException) {
                    // The SMS quota for the project has been exceeded
                    loadingDialog.dismiss()
                    Toast.makeText(
                        baseContext, resources.getString(R.string.error_message),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }

            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                storedVerificationId = p0
            }
        }
    }

    private fun startPhoneNumberVerification(phoneNumber: String) {
        callback()
        val options = PhoneAuthOptions.newBuilder(mAuth)
            .setPhoneNumber(phoneNumber)       // Phone number to verify
            .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
            .setActivity(this)                 // Activity (for callback binding)
            .setCallbacks(callbacks)          // OnVerificationStateChangedCallbacks
            .build()
        PhoneAuthProvider.verifyPhoneNumber(options)
    }
}