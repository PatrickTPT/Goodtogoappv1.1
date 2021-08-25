package com.example.goodtogoappv11

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.InputInStockAdapter
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.model.Constants.mediumList
import kotlinx.android.synthetic.main.activity_scan.*
import kotlinx.android.synthetic.main.item_bottom_infobox.rv_bottom_infobox

class BoxingActivity : BaseInputActivity(), OnAClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)
        childList.clear()


        val stationName = intent.getStringExtra("EXTRACT_STATION_NAME")

        val actionBar = supportActionBar
        if (stationName != null) {
            actionBar?.title = "裝箱｜${stationName}"
        } else {
            actionBar?.title = "裝箱｜調度站"
        }
        actionBar?.setDisplayHomeAsUpEnabled(true)






        idInputSlot = findViewById(R.id.et_idInputSlot)
        inputBtn = findViewById(R.id.button3)
        tv_box_count = findViewById(R.id.tv_box_count)
        tv_scanWord = findViewById(R.id.tv_scanWord)
        clearBtn = findViewById(R.id.btn_clear)
        proceedFab = findViewById(R.id.fab_proceed)


        setBottomBoxRecyclerView()


        //TODO: set should-have input

        //
        //var bottomBoxExpansionState = false
        ll_infobox_header.setOnClickListener {
            if (rv_bottom_infobox.visibility == View.GONE) {
                rv_bottom_infobox.visibility = View.VISIBLE
            } else {
                rv_bottom_infobox.visibility = View.GONE
            }

            //bottomBoxExpansionState = !bottomBoxExpansionState
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
            //inputCheck(ResultOfEditBoxInStockActivity())
            resolveBox()

        }


    }



    fun resolveBox() {
    if (childList.size > 0)
    {
        val newBox = createBox(childList)
        mediumList.add(newBox)
        //TODO: send request
        // response ok -> next page
              setResult(Activity.RESULT_OK)
        val intent = Intent(this, ResultOfBoxingInStockActivity::class.java)
        intent.putStringArrayListExtra("Test", inputList)
        intent.putExtra("NewBox",newBox.boxid)
        startActivity(intent)
        finish()


        // TODO: response error -> alert dialog
        // TODO: make bad cup visible




    } else
    {
        quickToast("無已登錄容器")
    }

    //TODO: add progressdialog at the proper places
}


    fun setBottomBoxRecyclerView(){
        rv_bottom_infobox.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        adapter = InputInStockAdapter(this, childList,this)
        rv_bottom_infobox.adapter = adapter
        rv_bottom_infobox.setHasFixedSize(true)
    }

    override fun onAClick(position: Int) {
        TODO("Not yet implemented")
    }





}
