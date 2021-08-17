package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.adapter.StoreListAdapter
import com.example.goodtogoappv11.data.Datasource
import kotlinx.android.synthetic.main.activity_test.*

class TestActivity : BaseActivity(), OnAClickListener {
    //private var adapter : StoreListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        titleText = "Choose Destination"
        setupLightActionBar()
        setupStoreRecyclerView()
        val storeList = Datasource().loadStores()

    }
    private fun setupStoreRecyclerView(){
        rv_store_list.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val adapter = StoreListAdapter(Datasource().loadStores(), this, this)
        rv_store_list.adapter = adapter
        rv_store_list.setHasFixedSize(true)

        //設置按鍵觸發
        /*adapter.setOnClickListener(object: StoreListAdapter.OnClickListener{
            override fun onClick(position: Int, title: String) {

                val intent = Intent(this@TestActivity, RecycleActivity::class.java)
                intent.putExtra(EXTRA_DETAILS, title)
                startActivity(intent)
                finish()
            }
        })*/


    }

    override fun onAClick(position: Int) {
        //Toast.makeText(this,"Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = Datasource().loadStores()[position]
        val intent = Intent(this@TestActivity, RecycleActivity::class.java)
        intent.putExtra("EXTRACT_STORE_NAME",clickedItem.name.toString())
        startActivity(intent)
        finish()
    }



    /*companion object{
        var EXTRA_DETAILS = "extra_details"
    }*/












}