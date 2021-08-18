package com.example.goodtogoappv11

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.goodtogoappv11.model.Box
import kotlinx.android.synthetic.main.activity_delivery_option.*
import java.text.SimpleDateFormat
import java.util.*


class DeliveryDetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_delivery_option)

        val getBoxId =intent.getStringExtra("boxid")

        tv_bottom_info_box_id.text = getBoxId.toString()

        val mToolBar = findViewById<Toolbar?>(R.id.tb_mToolbar)
        setSupportActionBar(mToolBar)
        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.title = "選擇出貨地點或日期"
            actionBar.setDisplayShowCustomEnabled(true)
        }



        //TODO:(setup actionbar and home return)
        //TODO:(select position popup)
        tv_select_destination.text = "好盒器基地"



        tv_select_deliveryDate.text = dateDisplay(Date())
        tv_select_deliveryDate.setOnClickListener{ view ->
            clickDatePicker(view)
        }


        fab_deliver_box.setOnClickListener{

            //Toast.makeText(this,"${tv_select_deliveryDate.text}", Toast.LENGTH_SHORT).show()
            //deliverThisBox(tv_select_destination.text.toString(),tv_select_deliveryDate.text.toString())
            setResult(android.app.Activity.RESULT_OK)
            finish()
            Toast.makeText(this,"安排出貨至：${tv_select_destination.text}", Toast.LENGTH_SHORT).show()

        }

    }

    private fun deliverThisBox(storeId: Int, deliveryDate: Date) {
        //TODO:date to millisecond,

    }

    private fun dateDisplay(date: Date) : String{

        val formatter = SimpleDateFormat("yyyy-MM-dd (E)")
        val dateToday = formatter.format(date)
        return dateToday
    }

    private fun clickDatePicker(view: View) {
        val myCalendar = Calendar.getInstance()
        val year = myCalendar.get(Calendar.YEAR)
        val month = myCalendar.get(Calendar.MONTH)
        val day = myCalendar.get(Calendar.DAY_OF_MONTH)

        // R.style.datepicker,
        val dpd = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener {
                _, selectedYear, selectedMonth, selectedDayOfMonth ->


            val selectedDate = "$selectedYear-${selectedMonth+1}-$selectedDayOfMonth"
            tv_select_deliveryDate.setText(selectedDate)
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)

        }
            ,year
            ,month
            ,day)

        //dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show()

    }

    fun bringToCertainList(newbox: Box){
        newbox.storeId = 17
        newbox.date


    }
}