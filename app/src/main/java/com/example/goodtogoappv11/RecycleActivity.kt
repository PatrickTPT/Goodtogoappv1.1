package com.example.goodtogoappv11

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import com.budiyev.android.codescanner.CodeScanner
import kotlinx.android.synthetic.main.activity_scan.*


class RecycleActivity : BaseInputActivity() {

    private lateinit var codeScanner: CodeScanner


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        val storeName = intent.getStringExtra("EXTRACT_STORE_NAME")

        //setupActionBar()

        //標題列設定
        val actionBar = supportActionBar
        actionBar!!.title = "回收"
        if (storeName != null) {
            actionBar.title = "回收(從 $storeName)"
        }
        actionBar.setDisplayHomeAsUpEnabled(true)


        idInputSlot = findViewById<EditText>(R.id.et_idInputSlot)
        inputBtn = findViewById<Button>(R.id.button3)
        tv_box_count = findViewById<TextView>(R.id.tv_box_count)
        tv_scanWord = findViewById(R.id.tv_scanWord)
        clearBtn = findViewById(R.id.btn_clear)
        proceedFab = findViewById(R.id.fab_proceed)


        ll_infobox_header.setOnClickListener {
           infoboxExpansion()
        }

        // 輸入鍵功能宣告
        inputBtn.setOnClickListener {
            inputAction()
        }

        //清除資料功能宣告
        clearBtn.setOnClickListener {
            infoboxDisplay()
        }

        //傳送資料功能宣告
        proceedFab.setOnClickListener {
            inputCheck(ResultOfRecycleActivity())

            //設定讀取fragment(暫緩）
            //val keyInFragment = keyInFragment()
            //supportFragmentManager.beginTransaction().replace(R.id.fragment_container,keyInFragment).commit()

        }
    }




    override fun onResume() {
        super.onResume()
        //codeScanner.startPreview()
    }

    override fun onPause() {
        //codeScanner.releaseResources()
        super.onPause()
    }



}


