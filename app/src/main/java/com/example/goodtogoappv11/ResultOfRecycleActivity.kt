package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ResultOfRecycleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val history = findViewById<TextView>(R.id.tv_history)
        val testing = intent.getStringArrayListExtra("successRecycleList")

        val resultTitle = findViewById<TextView>(R.id.tv_success_title)
        resultTitle.text = "回收成功"


        history.text = "${testing.toString().substring(1,testing.toString().length-1)}"
        val nextRecycle: Button = findViewById(R.id.nextRe_button)
        nextRecycle.visibility = View.VISIBLE

        val confirmButton = findViewById<Button>(R.id.confirm_button)


        confirmButton.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }


        nextRecycle.setOnClickListener {
            val intent = Intent(this, RecycleActivity::class.java)
            startActivity(intent)
            finish()
        }

    }



    }
