package com.example.goodtogoappv11

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.adapter.StoreListAdapter
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Store
import kotlinx.android.synthetic.main.activity_test.*
import java.util.*
import kotlin.collections.ArrayList

class TestActivity : BaseActivity(), OnAClickListener {


    lateinit var s: ArrayList<Store>
    private var storeList = ArrayList<Store>()
    private lateinit var searchView : androidx.appcompat.widget.SearchView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        titleText = String.format(getString(R.string.choose_destination))
        setupLightWeightActionBar()



        s = intent.getSerializableExtra("storeList") as ArrayList<Store>
        storeList.addAll(s)
        setupStoreRecyclerView()


        searchView = findViewById<SearchView>(R.id.sv_store_list)

        setupSearchView()

    }

    private fun setupSearchView() {

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText!=null) {
                    storeList.clear()
                    val search = newText!!.lowercase(Locale.getDefault())
                    s.forEach {
                        if (it.viewType == Constants.VIEW_TYPE_ONE) {
                            if (it.name!!.lowercase().contains(search)
                                || it.id.toString().lowercase().contains(search)
                            ) {
                                storeList.add(it)
                            }
                        }
                    }
                    rv_store_list.adapter?.notifyDataSetChanged()

                } else {
                    storeList.clear()
                    storeList.addAll(s)
                //sortByBoxingDateDescending()
                }
                return true
            }

        })


    }

    private fun setupStoreRecyclerView(){
        rv_store_list.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false)
        val adapter = StoreListAdapter(storeList, this, this)
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
        val clickedItem = storeList[position]
        val intent = Intent(this@TestActivity, RecycleActivity::class.java)
        intent.putExtra("EXTRACT_STORE_NAME",clickedItem.name.toString())
        startActivity(intent)
        finish()
    }















}