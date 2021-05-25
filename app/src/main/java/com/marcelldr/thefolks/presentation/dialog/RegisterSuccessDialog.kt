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
class RegisterSuccessDialog(activity: AppCompatActivity) {
    private var dialog: Dialog = Dialog(activity)
    private var successMessage: TextView
    private var successButton: Button

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.setContentView(R.layout.dialog_success)
        successMessage = dialog.findViewById(R.id.successMessage)
        successButton = dialog.findViewById(R.id.successButton)

        successMessage.text = "Congratulations, your account has been successfully created"
        successButton.setOnClickListener {
            dismiss()
            activity.finish()
        }
    }

    fun show() {
        dialog.show()
    }

    fun dismiss() {
        dialog.dismiss()
    }
}