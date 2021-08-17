package com.example.goodtogoappv11

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EditBoxToDeliverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)


        val actionBar = supportActionBar
        actionBar!!.title = "oops"
        actionBar.setDisplayHomeAsUpEnabled(true)
    }
}