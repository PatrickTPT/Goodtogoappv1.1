package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class EditBoxToDeliverActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)


        val actionBar = supportActionBar
        actionBar!!.title = "待配送｜修改裝箱"
        actionBar.setDisplayHomeAsUpEnabled(true)

        val fab = findViewById<FloatingActionButton>(R.id.fab_proceed)
        fab.setOnClickListener{
            val intent = Intent(this, ResultOfEditBoxToDeliverActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}