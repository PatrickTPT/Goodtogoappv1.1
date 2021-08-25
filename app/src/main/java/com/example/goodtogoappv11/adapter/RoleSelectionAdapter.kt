package com.example.goodtogoappv11.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.network.Role
import kotlinx.android.synthetic.main.layout_role_selection.view.*

class RoleSelectionAdapter(
    private val context: Context,
    private val items: ArrayList<Role>,
    private val listener: OnAClickListener) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener{

        val storeImage: ImageView? = view.iv_store_station_image
        val name: TextView? = view.tv_store_station_name

        init{
            view.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position: Int = absoluteAdapterPosition
            if(position != RecyclerView.NO_POSITION) {
                listener.onAClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ViewHolder(LayoutInflater.from(context)
            .inflate(R.layout.layout_role_selection, parent, false))
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = items[position]
        when (holder) {
         is ViewHolder -> {
             //holder.storeImage?.text = model.roleType
             if (model.roleType == "station") {
                  holder.name?.text = model.stationName
             } else if (model.roleType == "store"){
                 holder.name?.text = model.storeName
             } else {
                 holder.name?.text = "oops, something went wrong"
             }
         }
        }

    }

    override fun getItemCount(): Int {
        return items.size
    }


}
