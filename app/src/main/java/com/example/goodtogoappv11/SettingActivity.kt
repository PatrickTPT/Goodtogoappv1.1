package com.example.goodtogoappv11


import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_setting.*
import kotlin.system.exitProcess

class SettingActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)
        titleText = "設定"
        setupLightWeightActionBar()

        btn_sign_out.setOnClickListener(){

            val builder = AlertDialog.Builder(this)
            builder.setTitle("確定登出")
            builder.setMessage("Are you sure?")
            builder.setPositiveButton("登出") { dialog, _ ->
                dialog.dismiss()
                finish()
                exitProcess(-1)
            }
            builder.setNegativeButton("回去工作") { dialog, id ->
                dialog.cancel()

            }
            builder.show()

        }

    }
}