package com.example.goodtogoappv11

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.DispatchHistoryAdapter
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.data.Datasource
import com.example.goodtogoappv11.model.Constants
import kotlinx.android.synthetic.main.activity_dispatch_record.*
import java.util.*

class DispatchHistoryActivity : BaseActivity(), OnAClickListener {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dispatch_record)
        titleText = String.format(getString(R.string.dispatch_history_page))
        setupLightWeightActionBar()

        displayList.addAll(Datasource().loadBoxesbkup())

        setupRecyclerView()

    }

    private fun setupRecyclerView() {
        rv_dispatch_record.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = DispatchHistoryAdapter(this, displayList,this)

        rv_dispatch_record.adapter = adapter
        rv_dispatch_record.setHasFixedSize(true)

        if (rv_dispatch_record != null && displayList.size > 0) {
            tv_noContainer3_2.visibility = View.GONE
        } else {
            tv_noContainer3_2.visibility = View.VISIBLE
        }
    }

    override fun onAClick(position: Int) {
        val clickedItem = Datasource().loadBoxes()[position]
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
                        Constants.mediumList.forEach {
                            if (it.boxid.toString().lowercase().contains(search)
                                ||it.date.toString().lowercase().contains(search)) {
                                displayList.add(it)
                            }
                        }
                        adapter!!.notifyDataSetChanged()

                    } else {
                        displayList.clear()
                        displayList.addAll(Constants.mediumList)
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