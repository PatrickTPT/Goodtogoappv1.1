package com.example.goodtogoappv11.historyRecycleFragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.adapter.OnAClickListener
import com.example.goodtogoappv11.adapter.RecycleHistoryAdapter
import com.example.goodtogoappv11.data.Datasource
import com.example.goodtogoappv11.model.Constants.vReloadList
import com.example.goodtogoappv11.network.reload.ReloadResponseItem
import kotlinx.android.synthetic.main.fragment_general_recycle.view.*

class GeneralRecycleFragment : Fragment(), OnAClickListener {


    private var displayList= ArrayList<ReloadResponseItem>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //TODO: damn that hard
        // val rIntent = intent.getSerializableExtra("reloadList")
        // add ViewModel and Live Data to connect two pages
        // integrate with searching
        // remember to add sort when loading

        displayList.clear()
        displayList.addAll(vReloadList.filter{it.status == "reload"})



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        Log.d("fragment","general recycle fragment created!")



        val view = inflater.inflate(R.layout.fragment_general_recycle, container, false)

        view.rv_first_fragment.layoutManager = LinearLayoutManager(activity)
        view.rv_first_fragment.adapter = RecycleHistoryAdapter(requireContext(), displayList, this)
        view.rv_first_fragment.setHasFixedSize(true)

        return view
    }

    override fun onAClick(position: Int) {
        val clickedItem = Datasource().loadBoxes()[position]
    }
    /*private fun setupRecyclerView() {

        firstRecyclerView?.layoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        adapter = RecycleRecordAdapter(requireContext(), displayList)
        firstRecyclerView.adapter = adapter
        firstRecyclerView.setHasFixedSize(true)
        if (firstRecyclerView != null) {
            tv_no_container_4_2_1.visibility = View.GONE
        } else {
            tv_no_container_4_2_1.visibility = View.VISIBLE
        }
    }*/


}