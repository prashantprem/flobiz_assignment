package com.app.flobiz_assignment.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.app.flobiz_assignment.R


class FilterAdapter(
    val context: Context,
    val filters: ArrayList<String>,
    private val onTagClickInterface: OnTagClickInterface
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    private var selectedPosition = -1

    public fun setAllUnselected(call: Boolean) {
        if(call)
            selectedPosition = -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_filter, parent, false)
        return TagViewHolder(view)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is TagViewHolder) {

            if(selectedPosition == holder.absoluteAdapterPosition) {
                holder.tagCard.setCardBackgroundColor(context.resources.getColor(R.color.active))
            } else {
                holder.tagCard.setCardBackgroundColor(context.resources.getColor(R.color.in_active))
            }

            setHolderData(holder, filters[holder.absoluteAdapterPosition])

            holder.itemView.setOnClickListener {
                if(selectedPosition == holder.absoluteAdapterPosition) {
                    selectedPosition = -1;
                    holder.itemView.isSelected = false
                    holder.tagCard.setCardBackgroundColor(context.resources.getColor(R.color.active))
                } else {
                    selectedPosition = holder.absoluteAdapterPosition
                    holder.itemView.isSelected = true
                    holder.tagCard.setCardBackgroundColor(context.resources.getColor(R.color.in_active))
                    onTagClickInterface.onTagSelected(tag = filters.get(holder.absoluteAdapterPosition))
                }
                notifyDataSetChanged()
            }
        }
    }

    override fun getItemCount(): Int {
        if (filters.isNotEmpty()) {
            return filters.size
        }
        return 0
    }

    private fun setHolderData(
        holder: TagViewHolder,
        tag: String?
    ) {
        if(tag.isNullOrEmpty().not())
            holder.tagText.text = tag
    }


    inner class TagViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var tagCard = itemView.findViewById<CardView>(R.id.tag_card)
        var tagText = itemView.findViewById<TextView>(R.id.tv_tag)
    }


    interface OnTagClickInterface {
        fun onTagSelected(tag : String?) {}
    }
}