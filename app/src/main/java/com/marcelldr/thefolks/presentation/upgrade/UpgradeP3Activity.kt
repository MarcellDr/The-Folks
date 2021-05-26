package com.marcelldr.thefolks.presentation.upgrade

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityUpgradeP3Binding
import com.marcelldr.thefolks.presentation.access.LoginActivity
import com.marcelldr.thefolks.presentation.dialog.LoadingDialog
import com.marcelldr.thefolks.presentation.dialog.SuccessDialog
import org.koin.android.ext.android.inject
import java.io.File

class UpgradeP3Activity : AppCompatActivity() {
    private lateinit var binding: ActivityUpgradeP3Binding
    private val mAuth: FirebaseAuth by inject()
    private val db: FirebaseFirestore by inject()
    private val storage: FirebaseStorage by inject()
    private val user = mAuth.currentUser
    private var nik: String = ""
    private var name: String = ""
    private lateinit var outputDirectory: File
    private lateinit var ktpDirectory: File
    private lateinit var selfieDirectory: File

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
            binding = ActivityUpgradeP3Binding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.upgradeP3BackButton.setOnClickListener { finish() }
            binding.upgradeP3Button.setOnClickListener { submitForm() }
        }

        statusAndActionBar()
        binding()
        setImage()
    }

    private fun getOutputDirectory(): File {
        val mediaDir = externalMediaDirs.firstOrNull()?.let {
            File(it, resources.getString(R.string.app_name)).apply { mkdirs() }
        }
        return if (mediaDir != null && mediaDir.exists())
            mediaDir else filesDir
    }

    private fun setImage() {
        outputDirectory = getOutputDirectory()
        ktpDirectory = File(outputDirectory, "photo_ktp.jpg")
        selfieDirectory = File(outputDirectory, "photo_selfie.jpg")

        val bitmapKtp = BitmapFactory.decodeFile(ktpDirectory.absolutePath)
        val bitmapSelfie = BitmapFactory.decodeFile(selfieDirectory.absolutePath)

        val matrixKtp = Matrix()
        val matrixSelfie = Matrix()
        matrixKtp.postRotate(90F)
        matrixSelfie.postRotate(-90F)
        matrixSelfie.postScale(-1F, 1F)

        val rotateBitmapKtp = Bitmap.createBitmap(
            bitmapKtp,
            0,
            0,
            bitmapKtp.width,
            bitmapKtp.height,
            matrixKtp,
            true
        )
        val rotateBitmapPerson = Bitmap.createBitmap(
            bitmapSelfie,
            0,
            0,
            bitmapSelfie.width,
            bitmapSelfie.height,
            matrixSelfie,
            true
        )

        binding.upgradeP3Ktp.setImageBitmap(rotateBitmapKtp)
        binding.upgradeP3Selfie.setImageBitmap(rotateBitmapPerson)
    }

    private fun submitForm() {
        if (!validation()) {
            return
        }

        val upgradeSuccessDialog = SuccessDialog(
            this,
            resources.getString(R.string.upgrade_success_message)
        )
        submitWaitingList()
        submitPhotos()
        upgradeSuccessDialog.show()
        upgradeSuccessDialog.setOnContinueClickCallback(object :
            SuccessDialog.OnContinueClickCallback {
            override fun onClicked() {
                upgradeSuccessDialog.dismiss()
                val intent = Intent(this@UpgradeP3Activity, LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        })
    }

    private fun validation(): Boolean {
        val nikField = binding.upgradeP3NikField.text.toString()
        val nameField = binding.upgradeP3NameField.text.toString()
        return if (nikField.isNotEmpty()) {
            nik = nikField
            binding.upgradeP3NikAlert.isErrorEnabled = false
            if (nameField.isNotEmpty()) {
                name = nameField
                binding.upgradeP3NameAlert.isErrorEnabled = false
                true
            } else {
                binding.upgradeP3NameAlert.error = "Nama lengkap tidak boleh kosong"
                binding.upgradeP3NameAlert.isErrorEnabled = true
                false
            }
        } else {
            binding.upgradeP3NikAlert.error = "NIK tidak boleh kosong"
            binding.upgradeP3NikAlert.isErrorEnabled = true
            false
        }
    }

    private fun submitWaitingList() {
        val data = HashMap<String, Any>()
        data["uid"] = user?.uid.toString()
        data["nik"] = nik
        data["name"] = name
        db.collection("waiting_list").document(user?.uid.toString()).set(data)
        db.collection("users").document(user?.uid.toString()).update("status", "waiting")
    }

    private fun submitPhotos() {
        val ref = storage.reference
        ref.child("users").child(user?.uid.toString()).child("photo_ktp.jpg")
            .putFile(Uri.fromFile(ktpDirectory))
        ref.child("users").child(user?.uid.toString()).child("photo_selfie.jpg")
            .putFile(Uri.fromFile(selfieDirectory))
    }
}