package com.example.goodtogoappv11

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.example.goodtogoappv11.adapter.RecycleHistoryViewPagerAdapter
import com.example.goodtogoappv11.data.Datasource
import com.example.goodtogoappv11.model.Constants.marrayList
import com.example.goodtogoappv11.recycleHistoryFragment.CleanRecycleFragment
import com.example.goodtogoappv11.recycleHistoryFragment.GeneralRecycleFragment
import kotlinx.android.synthetic.main.activity_recycle_history.*
import java.util.*

class RecycleHistoryActivity : BaseActivity() {

    //private var adapter : RecycleRecordAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_history)
        titleText = "回收紀錄"
        setupLightActionBar()
        marrayList.addAll(Datasource().loadBoxesbkup())
        displayList.addAll(Datasource().loadBoxesbkup())

        //setupRecyclerView()
        setupTabs()
    }

    /*private fun setupRecyclerView() {
        rv_recycle_history.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = RecycleRecordAdapter(this, displayList)
        rv_recycle_history.adapter = adapter
        rv_recycle_history.setHasFixedSize(true)
        if (rv_recycle_history != null) {
            tv_noContainer4_2.visibility = View.GONE
        } else {
            tv_noContainer4_2.visibility = View.VISIBLE
        }
    }*/

    private fun setupTabs() {
        val adapter = RecycleHistoryViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(GeneralRecycleFragment(),"一般回收")
        adapter.addFragment(CleanRecycleFragment(),"乾淨回收")
        vp_recycle_history.adapter = adapter
        tb_recycle_history.setupWithViewPager(vp_recycle_history)
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