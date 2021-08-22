package com.example.goodtogoappv11

import android.os.Bundle

class EditBoxInStockActivity : BaseInputActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        val actionBar = supportActionBar
        actionBar!!.title = "庫存｜修改裝箱"
        actionBar.setDisplayHomeAsUpEnabled(true)

        idInputSlot = findViewById(R.id.et_idInputSlot)
        inputBtn = findViewById(R.id.button3)
        tv_box_count = findViewById(R.id.tv_box_count)
        tv_scanWord = findViewById(R.id.tv_scanWord)
        clearBtn = findViewById(R.id.btn_clear)
        proceedFab = findViewById(R.id.fab_proceed)

        //TODO: set recyclerView of current box child
        //TODO: set should-have input

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
            inputCheck(ResultOfEditBoxInStockActivity())
        }





    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}