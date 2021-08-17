package com.example.goodtogoappv11.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.model.Constants.VIEW_TYPE_ONE
import com.example.goodtogoappv11.model.Store
import kotlinx.android.synthetic.main.item_store.view.*
import kotlinx.android.synthetic.main.item_tag_layout.view.*

class StoreListAdapter(
    private val items:ArrayList<Store>, val context: Context,
    private val listener: OnAClickListener
    ):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        //兩個viewholder
        if (viewType == VIEW_TYPE_ONE){
            return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_store, parent, false))
        } else  {
            return TagViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tag_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = items[position]

        //兩個holder版做判定
        when (holder) {
            is ViewHolder -> {
                holder.name?.text = model.name
                holder.info?.text = "ID: ${model.id} - ${model.type}"
            }
            is TagViewHolder -> {
                holder.label?.text = model.type
                if(holder.label?.text == "Movie") {
                    holder.lltest?.visibility = View.GONE
                } else {
                    holder.lltest?.visibility = View. VISIBLE
                }
            }
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
    View.OnClickListener{

        val name: TextView? = view.tv_storeName
        val info: TextView? = view.tv_storeInfo

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onAClick(position)
            }
        }
    }


    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val label: TextView? = view.tv_tagName
        val lltest: LinearLayout? = view.ll_test
    }






















}