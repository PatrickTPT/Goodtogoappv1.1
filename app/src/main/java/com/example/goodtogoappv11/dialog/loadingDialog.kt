package com.example.goodtogoappv11.dialog

import android.app.Dialog
import android.content.Context
import com.example.goodtogoappv11.R

interface loadingDialog {

    fun customProgressDialogFunction() {
        val mContext: Context? = null
        val customProgressDialog = Dialog(mContext!!)

        customProgressDialog.setContentView(R.layout.dialog_custom_progress)
        customProgressDialog.show()
    }
}