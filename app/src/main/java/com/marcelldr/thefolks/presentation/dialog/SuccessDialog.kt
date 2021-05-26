package com.marcelldr.thefolks.presentation.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.marcelldr.thefolks.R

@SuppressLint("SetTextI18n")
class SuccessDialog(activity: AppCompatActivity, message: String) {
    private var dialog: Dialog = Dialog(activity)
    private var successMessage: TextView
    private var successButton: Button
    private lateinit var onContinueClickCallback: OnContinueClickCallback

    interface OnContinueClickCallback {
        fun onClicked()
    }

    fun setOnContinueClickCallback(onContinueClickCallback: OnContinueClickCallback) {
        this.onContinueClickCallback = onContinueClickCallback
    }

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_success)
        successMessage = dialog.findViewById(R.id.successMessage)
        successButton = dialog.findViewById(R.id.successButton)

        successMessage.text = message
        successButton.setOnClickListener { onContinueClickCallback.onClicked() }
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}