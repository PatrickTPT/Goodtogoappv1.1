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
import java.text.SimpleDateFormat
import java.util.*

class RecycleHistoryAdapter(
    private val context: Context,
    private val items: ArrayList<Box>,
    private val listener: OnAClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val boxId: TextView? = view.tv_box_id
        val boxTitle: TextView? = view.tv_box_title
        val cupType: TextView? = view.tv_cup_type
        val cupNumber: TextView? = view.tv_cup_number

        val llWholeItem: LinearLayout = view.ll_whole_item

        val llboxinfo: LinearLayout? = view.ll_box_info
        val llparent: LinearLayout? = view.shown_box_info_layout

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
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        if (viewType == Constants.VIEW_TYPE_ONE){
            return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_together_layout, parent, false))
        } else  {
            return TagViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tag_layout, parent, false))
        }    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = items[position]
        val isExpandable : Boolean = items[position].expandable

        when (holder) {
            is ViewHolder -> {
                holder.boxId?.visibility = View.GONE
                holder.boxTitle?.text =
                    "${ SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN).format(model.date) } 回收｜${model.storeName}"
                holder.cupType?.text = model.cupType.toString()
                holder.cupNumber?.text = model.cupNumber.toString()

                holder.llboxinfo?.visibility = if (isExpandable) View.VISIBLE else View.GONE
                holder.llparent?.setOnClickListener{
                    model.expandable = !model.expandable
                    notifyItemChanged(position)
                }
                /*holder.llWholeItem.visibility =
                    if(model.status == Constants.BOX_STATUS_TOBEUSED) View.VISIBLE else View.GONE*/

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
