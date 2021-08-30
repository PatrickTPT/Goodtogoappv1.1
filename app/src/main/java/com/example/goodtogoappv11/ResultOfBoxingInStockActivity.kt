package com.example.goodtogoappv11

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class ResultOfBoxingInStockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val history = findViewById<TextView>(R.id.tv_history)
        //val testing =intent.getStringArrayListExtra("NewBox")
        val testing =intent.getIntExtra("NewBox", 0)

        val resultTitle = findViewById<TextView>(R.id.tv_success_title)
        resultTitle.text = String.format(getString(R.string.finish_boxing_in_stock))

        history.text = String.format(getString(R.string.number_with_hashtag),testing)



        val confirmButton = findViewById<Button>(R.id.confirm_button)
        val nextReButton : Button = findViewById(R.id.nextRe_button)
        nextReButton.visibility = View.GONE

        confirmButton.setOnClickListener {
            //TODO ("add result?")
            finish()
        }




    }



    }
