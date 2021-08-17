package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val history = findViewById<TextView>(R.id.history)
        val testing =intent.getStringArrayListExtra("Test")


        history.text = "$testing"
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
