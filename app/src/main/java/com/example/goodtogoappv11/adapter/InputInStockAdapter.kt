package com.example.goodtogoappv11.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.model.Boxes
import com.example.goodtogoappv11.model.Constants
import kotlinx.android.synthetic.main.item_single_cup_infobox.view.*
import kotlinx.android.synthetic.main.item_tag_layout.view.*

class InputInStockAdapter(
    private val context: Context,
    private val items: ArrayList<Boxes.BoxChild>,
    private val listener: OnAClickListener
    ): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        var cupType : TextView = view.tv_cup_type_bottom
        var cupNumber : TextView = view.tv_cup_number_bottom
        val cupVeri : TextView = view.tv_cup_verification_state
        val deleteCup : ImageView = view.iv_cup_delete_bottom


        init{
            view.setOnClickListener(this)

            deleteCup.setOnClickListener{
                Toast.makeText(context,"How dare you!",Toast.LENGTH_SHORT).show()
                items.removeAt(absoluteAdapterPosition)

                notifyDataSetChanged()

            }
        }


        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onAClick(position)
            }
        }

    }

    class TagViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val label: TextView? = view.tv_tagName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        if (viewType == Constants.VIEW_TYPE_ONE) {
            return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_single_cup_infobox, parent, false))
        } else {
            return InStockAdapter.TagViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tag_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = items[position]
        when (holder) {
            is ViewHolder -> {
                holder.cupNumber.text = String.format(context.getString(R.string.number_with_hashtag),model.cupId)
                //holder.cupNumber.text = "# ${model.cupId}"
                holder.cupType.text = model.cupType.toString()
            }
            is TagViewHolder -> {
                holder.label?.text = model.cupType
            }
        }
    }

    override fun getItemCount() = items.size

    override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }



}