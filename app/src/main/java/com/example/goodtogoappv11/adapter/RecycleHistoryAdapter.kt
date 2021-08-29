package com.example.goodtogoappv11.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.goodtogoappv11.R
import com.example.goodtogoappv11.model.Constants
import com.example.goodtogoappv11.model.Constants.storeList
import com.example.goodtogoappv11.network.reload.ReloadResponseItem
import kotlinx.android.synthetic.main.item_tag_layout.view.*
import kotlinx.android.synthetic.main.item_together_layout.view.*
import java.text.SimpleDateFormat
import java.util.*

class RecycleHistoryAdapter(
    private val context: Context,
    private val items: ArrayList<ReloadResponseItem>,
    private val listener: OnAClickListener
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>()  {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view),
        View.OnClickListener {
        val boxId: TextView? = view.tv_box_id
        val boxTitle: TextView? = view.tv_box_title

        val cupType: TextView? = view.tv_cup_type1
        val cupNumber: TextView? = view.tv_cup_number1
        val cupTypeInfo2: LinearLayout? = view.ll_box_content2
        val cupType2: TextView? = view.tv_cup_type2
        val cupNumber2: TextView? = view.tv_cup_number2
        val cupTypeInfo3: LinearLayout? = view.ll_box_content3
        val cupTypeMore: TextView? = view.tv_more_type

        val boxDetail: TextView? = view.tv_box_detail

        val llboxinfo: LinearLayout = view.ll_box_info
        val llparent: LinearLayout = view.shown_box_info_layout

        var IS_VISIBLE : Int? = llboxinfo?.visibility

        init {
            view.setOnClickListener(this)

            llparent.setOnClickListener{
                if (llboxinfo.visibility == View.GONE){
                    llboxinfo.visibility = View.VISIBLE
                } else {
                    llboxinfo.visibility = View.GONE
                }

            }

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


        if (viewType != Constants.VIEW_TYPE_ONE){
            return ViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_together_layout, parent, false))
        } else  {
            return TagViewHolder(LayoutInflater.from(context)
                .inflate(R.layout.item_tag_layout, parent, false))
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val model = items[position]
        val idToName = storeList.first{it.id == model.storeID}
        val containerListToString = model.containerList.toString()
        val containerListNoBracket = containerListToString.substring(1,containerListToString.length-1)
        val sdf=SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'",Locale.ENGLISH)
        //sdf.timeZone = TimeZone.getTimeZone("UTC")
        val parsedDate = sdf.parse(model.action[0].timestamps)
        val lsdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.TAIWAN)
        lsdf.timeZone = TimeZone.getTimeZone("Asia/Taipei")
        val localDate = lsdf.format(parsedDate)


        val parseString = ""
        for(i in model.deliverContent.indices){
            parseString +
                    "${ model.deliverContent.get(i).containerType }\n" +
                    "${ model.deliverContent.get(i).amount}\n"
        }




        when (holder) {
            is ViewHolder -> {
                holder.boxId?.visibility = View.GONE
                holder.boxTitle?.text =
                    "${model.action[0].timestamps.substring(0,10)} " +
                            "回收｜${idToName.name}"


                holder.cupType?.text = model.deliverContent[0].containerType
                holder.cupNumber?.text = model.deliverContent[0].amount.toString()


                if(model.deliverContent.size > 1 ){
                    holder.cupTypeInfo2?.visibility = View.VISIBLE
                    holder.cupType2?.text = model.deliverContent[1].containerType
                    holder.cupNumber2?.text = model.deliverContent[1].amount.toString()
                }

                if(model.deliverContent.size > 2 ){
                    holder.cupTypeInfo3?.visibility = View.VISIBLE
                    holder.cupTypeMore?.text = "還有${model.deliverContent.size - 2}種貨品"
                }


                //holder.llboxinfo?.visibility = if (isExpandable) View.VISIBLE else View.GONE
                holder.boxDetail?.text =
                    "${model.action[0].boxStatus}｜" +
                            "${model.action[0].phone}｜" +
                            "$localDate\n" +
                            containerListNoBracket +
                            parseString

                //TODO: timestamps轉換






            }
            is TagViewHolder -> {
                holder.label?.text = model.action[0].timestamps

            }

        }
    }

    /*private fun getCupDetail(position: Int): String {
        val model = items[position]
            var parseString: String = ""
            for(i in model.deliverContent.indices){
                parseString +
                        "${ model.deliverContent.get(i).containerType }\n" +
                        "${ model.deliverContent.get(i).amount}\n"
            }
            return parseString
    }*/

    override fun getItemCount() = items.size

    /*override fun getItemViewType(position: Int): Int {
        return items[position].viewType
    }*/


    enum class ReloadStatus(name: String) {
        reload("回收"),
        cleanReload("乾淨回收")

    }

    enum class BoxAction(name:String){
        archived("封存")
    }
}
