package com.marcelldr.thefolks.presentation.upgrade

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.marcelldr.thefolks.R
import com.marcelldr.thefolks.databinding.ActivityPhotoReviewBinding

class PhotoReviewActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPhotoReviewBinding
    private lateinit var type: String
    private lateinit var path: String

    companion object {
        const val TYPE = "type"
        const val PATH = "path"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setUp()
    }

    private fun setUp() {
        fun statusAndActionBar() {
            window.statusBarColor = resources.getColor(R.color.grey, this.theme)
            supportActionBar?.hide()
        }

        fun intent() {
            type = intent.getStringExtra(TYPE) as String
            path = intent.getStringExtra(PATH) as String
        }

        fun setImage() {
            val bitmap = BitmapFactory.decodeFile(path)
            val matrix = Matrix()
            if(type == "KTP") {
                matrix.postRotate(90F)
            } else {
                matrix.postRotate(-90F)
                matrix.postScale(-1F, 1F)
            }
            val rotateBitmap =
                Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
            binding.reviewImage.setImageBitmap(rotateBitmap)
        }

        fun binding() {
            binding = ActivityPhotoReviewBinding.inflate(layoutInflater)
            setContentView(binding.root)

            if(type == "KTP") {
                binding.reviewTitle.text = resources.getString(R.string.review_ktp_title)
                binding.reviewSubtitle.text = resources.getString(R.string.review_ktp_subtitle)
                binding.reviewRetake.setOnClickListener {
                    startActivity(Intent(this@PhotoReviewActivity, PhotoKtpActivity::class.java))
                    finish()
                }
                binding.reviewButton.setOnClickListener {
                    startActivity(Intent(this@PhotoReviewActivity, UpgradeP2Activity::class.java))
                    finish()
                }
            } else {
                binding.reviewTitle.text = resources.getString(R.string.review_selfie_title)
                binding.reviewSubtitle.text = resources.getString(R.string.review_selfie_subtitle)
                binding.reviewRetake.setOnClickListener {
                    startActivity(Intent(this@PhotoReviewActivity, PhotoSelfieActivity::class.java))
                    finish()
                }
                binding.reviewButton.setOnClickListener {
                    startActivity(Intent(this@PhotoReviewActivity, UpgradeP3Activity::class.java))
                    finish()
                }
            }

            binding.reviewBackButton.setOnClickListener { finish() }

            setImage()
        }

        statusAndActionBar()
        intent()
        binding()
    }
}