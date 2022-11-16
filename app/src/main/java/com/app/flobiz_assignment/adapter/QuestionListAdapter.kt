package com.app.flobiz_assignment.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.flobiz_assignment.R
import com.app.flobiz_assignment.models.QuestionResponse.Item
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import org.w3c.dom.Text

class QuestionListAdapter(private val mList: List<Item>) : RecyclerView.Adapter<QuestionListAdapter.ViewHolder>(){

    private var context: Context? = null


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        context = parent.context
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_rcv,parent,false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = mList[position]
        holder.name.text = item.owner.displayName
        holder.title.text = item.title
        holder.posted.text = item.creationDate.toString()
        context?.let {
            Glide.with(it)
                .load(item.owner.profileImage)
                .transform(CircleCrop())
                .into(holder.profile)
        }
    }

    override fun getItemCount(): Int {
       return mList.size
    }

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.item_title)
        val profile: ImageView = itemView.findViewById(R.id.item_profile)
        val name: TextView = itemView.findViewById(R.id.item_name)
        val posted: TextView = itemView.findViewById(R.id.item_created_on)

    }
}