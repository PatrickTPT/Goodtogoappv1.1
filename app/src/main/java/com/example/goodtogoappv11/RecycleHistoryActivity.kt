package com.example.goodtogoappv11

import android.os.Bundle
import com.example.goodtogoappv11.adapter.RecycleHistoryViewPagerAdapter
import com.example.goodtogoappv11.historyRecycleFragment.CleanRecycleFragment
import com.example.goodtogoappv11.historyRecycleFragment.GeneralRecycleFragment
import com.example.goodtogoappv11.model.Constants.mediumList
import kotlinx.android.synthetic.main.activity_recycle_history.*

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
        //quickToast("$i")
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





}