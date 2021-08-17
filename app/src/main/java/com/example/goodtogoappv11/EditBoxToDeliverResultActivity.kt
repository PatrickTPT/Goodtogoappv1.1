package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class EditBoxToDeliverResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val history = findViewById<TextView>(R.id.history)
        val testing =intent.getStringArrayListExtra("Test")

        val resultTitle = findViewById<TextView>(R.id.tv_success_title)
        resultTitle.text = "修改裝箱完成"

        history.text = "$testing"


        val confirmButton = findViewById<Button>(R.id.confirm_button)
        val nextReButton : Button = findViewById(R.id.nextRe_button)
        nextReButton.visibility = View.GONE

        confirmButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }




    }



    }
