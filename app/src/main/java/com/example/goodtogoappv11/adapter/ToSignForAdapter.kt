package com.example.goodtogoappv11.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.model.Box
import com.example.goodtogoappv11.model.Constants
import kotlinx.android.synthetic.main.item_tag_layout.view.*
import kotlinx.android.synthetic.main.item_together_layout.view.*

class ToSignForAdapter(
    private val context: Context,
    private val items: ArrayList<Box>, //1
    private val listener: OnAClickListener):
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener{ //2
        val boxId: TextView? = view.tv_box_id
        val lltosignfor: LinearLayout? = view.ll_to_sign_for

        //3
        val llboxinfo: LinearLayout? = view.ll_box_info
        val llparent: LinearLayout? = view.shown_box_info_layout

        //4
        init {
            view.setOnClickListener(this)
            //TODO: add sign boxes function, including to hide the bar and to add history
        }

        //5
        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onAClick(position)
            }
        }
    }

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val label: TextView? = view.tv_tagName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == Constants.VIEW_TYPE_ONE){
            return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_together_layout, parent, false))
        } else  {
            return TagViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tag_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = items[position]
        val isExpandable : Boolean = items[position].expandable //6


        when (holder) {
            is ViewHolder -> {
                holder.boxId?.text = model.boxid.toString()
                holder.lltosignfor?.visibility = View.VISIBLE
                //7
                holder.llboxinfo?.visibility = if (isExpandable) View.VISIBLE else View.GONE
                holder.llparent?.setOnClickListener{
                    model.expandable = !model.expandable
                    notifyItemChanged(position)
                }

            }
            is TagViewHolder -> {
                holder.label?.text = model.date.toString()

            }

        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }
}