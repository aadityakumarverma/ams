package com.ams.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ams.R
import com.ams.databinding.LayoutUpcomingCompetitionsBinding
import com.ams.utils.UtilsFunctions.animateItem
import com.ams.utils.UtilsFunctions.setOnClickListeners

class UpcomingCompetitionsAdapter(val context: Context, private val arrListItems: MutableList<Int>,
                                  private val click: OnCompetitionClickListener
) : RecyclerView.Adapter<UpcomingCompetitionsAdapter.ViewHolder>(){

    interface OnCompetitionClickListener {
        fun onCompetitionClick(item: String)
    }

    class ViewHolder(val binding: LayoutUpcomingCompetitionsBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = LayoutUpcomingCompetitionsBinding.inflate(inflater, parent, false)
        R.layout.layout_upcoming_competitions
        val lp = binding.root.layoutParams
        lp.width = (parent.measuredWidth / 1.2).toInt()
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrListItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        arrListItems[position].apply {

            with(holder.binding) {
                llItem.setBackgroundResource(this@apply)
                root.setOnClickListeners {

                }
            }
        }
        animateItem(holder.itemView, position)
    }

    fun addAll(newData: MutableList<Int>) {
        newData?.let {
            arrListItems.addAll(it)
            notifyDataSetChanged()
        }
    }


    fun clearAll() {
        arrListItems.clear()
        notifyDataSetChanged()
    }

}