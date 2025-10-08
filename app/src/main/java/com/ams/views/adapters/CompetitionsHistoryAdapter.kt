package com.ams.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.ams.R
import com.ams.databinding.LayoutCompetitionsHistoryBinding
import com.ams.domain.models.History
import com.ams.utils.UtilsFunctions.animateItem
import com.ams.utils.UtilsFunctions.setOnClickListeners

class CompetitionsHistoryAdapter(val context: Context, private val arrListItems: MutableList<History>,
                                 private val click: OnHistoryClickListener
) : RecyclerView.Adapter<CompetitionsHistoryAdapter.ViewHolder>(){

    interface OnHistoryClickListener {
        fun onHistoryClick(item: String)
    }

    class ViewHolder(val binding: LayoutCompetitionsHistoryBinding) : RecyclerView.ViewHolder(binding.root) {

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(context)
        val binding = LayoutCompetitionsHistoryBinding.inflate(inflater, parent, false)
        R.layout.layout_competitions_history
//        val lp = binding.root.layoutParams
//        lp.width = (parent.measuredWidth / 1.2).toInt()
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return arrListItems.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        arrListItems[position].apply {

            with(holder.binding) {
                ivMedal.setBackgroundResource(color)
                tvDate.text = date
                tvMonth.text = month
                tvCompetitionName.text = name
                root.setOnClickListeners {

                }
            }
        }
        animateItem(holder.itemView, position)
    }

    fun addAll(newData: MutableList<History>) {
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