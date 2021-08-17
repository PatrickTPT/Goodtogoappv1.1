package com.example.goodtogoappv11

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.DispatchRecordAdapter
import com.example.goodtogoappv11.data.Datasource
import com.example.goodtogoappv11.model.Constants.marrayList
import kotlinx.android.synthetic.main.activity_dispatch_record.*
import java.util.*

class DispatchRecordActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_record)
        titleText = "調度紀錄"
        setupLightWeightActionBar()

        displayList.addAll(Datasource().loadBoxesbkup())

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        rv_dispatch_record.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = DispatchRecordAdapter(this, displayList)
        rv_dispatch_record.adapter = adapter
        rv_dispatch_record.setHasFixedSize(true)
        if (rv_dispatch_record != null) {
            tv_noContainer3_2.visibility = View.GONE
        } else {
            tv_noContainer3_2.visibility = View.VISIBLE
        }
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu!!.findItem(R.id.mu_search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            val editText =
                searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

            editText.hint = "箱子ID..."
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        val search = newText.lowercase(Locale.getDefault())
                        marrayList.forEach {
                            if (it.boxid.toString().lowercase().contains(search)
                                ||it.date.toString().lowercase().contains(search)) {
                                displayList.add(it)
                            }
                        }
                        adapter!!.notifyDataSetChanged()

                    } else {
                        displayList.clear()
                        displayList.addAll(marrayList)
                        adapter!!.notifyDataSetChanged()
                    }
                    return true
                }

            })

        }

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }
}