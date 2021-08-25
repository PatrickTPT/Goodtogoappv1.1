package com.example.goodtogoappv11

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import com.example.goodtogoappv11.adapter.RecycleHistoryViewPagerAdapter
import com.example.goodtogoappv11.historyRecycleFragment.CleanRecycleFragment
import com.example.goodtogoappv11.historyRecycleFragment.GeneralRecycleFragment
import com.example.goodtogoappv11.model.Constants.mediumList
import kotlinx.android.synthetic.main.activity_recycle_history.*
import java.util.*

class RecycleHistoryActivity : BaseActivity() {

     //override var adapter : RecyclerView.Adapter<RecyclerView.ViewHolder>? = null as RecycleRecordAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycle_history)
        titleText = String.format(getString(R.string.recycle_history_page))
        setupLightWeightActionBar()
        displayList.clear()
        displayList.addAll(mediumList)


        val i = intent.getSerializableExtra("reloadList")
        quickToast("$i")
        //TODO: delete this test after livedata created

        setupTabs()
    }

    private fun setupTabs() {
        val adapter = RecycleHistoryViewPagerAdapter(supportFragmentManager)
        adapter.addFragment(GeneralRecycleFragment(),"一般回收")
        adapter.addFragment(CleanRecycleFragment(),"乾淨回收")
        vp_recycle_history.adapter = adapter
        tb_recycle_history.setupWithViewPager(vp_recycle_history)
    }

    /*override fun onAClick(position: Int) {
        val clickedItem = Datasource().loadBoxes()[position]
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu!!.findItem(R.id.mu_search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            val editText =
                searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

            editText.hint = String.format(getString(R.string.input_hint_box_id_cup_id))
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText!!.isNotEmpty()) {
                        displayList.clear()
                        val search = newText.lowercase(Locale.getDefault())
                        mediumList.forEach {
                            if (it.boxid.toString().lowercase().contains(search)
                                ||it.date.toString().lowercase().contains(search)) {
                                displayList.add(it)
                            }
                        }
                        adapter?.notifyDataSetChanged()

                    } else {
                        displayList.clear()
                        displayList.addAll(mediumList)
                        adapter?.notifyDataSetChanged()
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