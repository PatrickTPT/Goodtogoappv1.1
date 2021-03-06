package com.example.goodtogoappv11

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.adapter.ToSignForAdapter
import com.example.goodtogoappv11.data.Datasource
import com.example.goodtogoappv11.model.Constants.marrayList
import kotlinx.android.synthetic.main.activity_to_sign_for.*
import java.util.*

class ToSignForActivity : BaseActivity(), OnAClickListener {

    //private var adapter: ToSignForAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_sign_for)
        titleText = "待簽收"
        setupLightWeightActionBar()
        marrayList.addAll(Datasource().loadBoxesbkup())
        displayList.addAll(Datasource().loadBoxesbkup())

        setupToSignForRecyclerView()
    }

    private fun setupToSignForRecyclerView() {

        rv_to_sign_for.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = ToSignForAdapter(this, displayList,this)
        rv_to_sign_for.adapter = adapter
        rv_to_sign_for.setHasFixedSize(true)

        if (rv_to_sign_for != null) {
            tv_noContainer3_1.visibility = View.GONE
        } else {
            tv_noContainer3_1.visibility = View.VISIBLE
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