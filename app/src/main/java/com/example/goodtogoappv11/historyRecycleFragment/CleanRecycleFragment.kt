package com.example.goodtogoappv11.historyRecycleFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.*
import android.widget.EditText
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.adapter.RecycleHistoryAdapter
import com.example.goodtogoappv11.data.Datasource
import com.example.goodtogoappv11.model.Box
import com.example.goodtogoappv11.model.Constants
import kotlinx.android.synthetic.main.fragment_clean_recycle.view.*
import java.util.*
import kotlin.collections.ArrayList

class CleanRecycleFragment : Fragment(), OnAClickListener {

    private var displayList= ArrayList<Box>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        displayList.clear()
        displayList.addAll(Constants.mediumList)

        //setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_clean_recycle, container, false)

        view.rv_second_fragment.layoutManager = LinearLayoutManager(activity)
        view.rv_second_fragment.adapter = RecycleHistoryAdapter(requireContext(), displayList,this)
        view.rv_second_fragment.setHasFixedSize(true)

        return view
    }

    override fun onAClick(position: Int) {
        val clickedItem = Datasource().loadBoxes()[position]
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {

        //menuInflater.inflate(R.menu.menu_search, menu)
        val menuItem = menu!!.findItem(R.id.mu_search)
        if (menuItem != null) {
            val searchView = menuItem.actionView as SearchView

            val editText =
                searchView.findViewById<EditText>(androidx.appcompat.R.id.search_src_text)

            editText.hint = String.format(getString(R.string.input_hint_cup_id))
            searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }

                @SuppressLint("NotifyDataSetChanged")
                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText != null) {
                        displayList.clear()
                        val search = newText.lowercase(Locale.getDefault())
                        Constants.mediumList.forEach {
                            if (it.boxid.toString().lowercase().contains(search)
                                ||it.date.toString().lowercase().contains(search)) {
                                displayList.add(it)
                            }
                        }
                        view!!.rv_second_fragment.adapter?.notifyDataSetChanged()

                    } else {
                        displayList.clear()
                        displayList.addAll(Constants.mediumList)
                        view!!.rv_second_fragment.adapter?.notifyDataSetChanged()
                    }
                    return true
                }

            })

        }


    }

}