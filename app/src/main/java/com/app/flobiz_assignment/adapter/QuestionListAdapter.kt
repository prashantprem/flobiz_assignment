package com.app.flobiz_assignment.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.app.Utils
import com.app.flobiz_assignment.R
import com.app.flobiz_assignment.models.QuestionResponse.Item
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop

class QuestionListAdapter(private var mList: List<Item>) : RecyclerView.Adapter<RecyclerView.ViewHolder>(){

    private var context: Context? = null
    private var count = 0


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        context = parent.context
        if(viewType == 0){
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_placeholder,parent,false)
            return PlaceHolderViewHolder(view)
        } else {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_rcv,parent,false)
            return MainViewHolder(view)
        }

    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        if(holder is MainViewHolder){
            val item = mList[position]
            holder.name.text = item.owner.displayName
            holder.title.text = item.title
            holder.posted.text = item.creationDate.let {
                Utils.formatTimeStamp(it)
            }
            context?.let {
                Glide.with(it)
                    .load(item.owner.profileImage)
                    .transform(CircleCrop())
                    .into(holder.profile)
            }

            holder.itemView.setOnClickListener {
                openInBrowser(item.link)
            }
        } else if(holder is PlaceHolderViewHolder){
            val pref = context?.getSharedPreferences("placeholder",Context.MODE_PRIVATE)
            if(pref?.getInt("clicked",0)!! >= 3){
                holder.itemView.visibility = View.GONE
            }

            holder.hide_btn.setOnClickListener{
                count++
                if(count >= 3){
                    holder.itemView.visibility = View.GONE
                }
                val editor = pref.edit()
                editor?.putInt("clicked",count)
                editor?.commit()
            }
        }

    }

    override fun getItemCount(): Int {
        if(mList.isNotEmpty()){
            return mList.size + 1
        }
       return 0
    }

    override fun getItemViewType(position: Int): Int {
        return if(position == mList.size)
            0
        else
            1
    }

    class MainViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val title: TextView = itemView.findViewById(R.id.item_title)
        val profile: ImageView = itemView.findViewById(R.id.item_profile)
        val name: TextView = itemView.findViewById(R.id.item_name)
        val posted: TextView = itemView.findViewById(R.id.item_created_on)

    }

    fun filterList(filterList: List<Item>) {
        // below line is to add our filtered
        // list in our course array list.
        mList = filterList
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged()
    }

    private fun openInBrowser(url: String) {
        val openURL = Intent(Intent.ACTION_VIEW)
        openURL.data = Uri.parse(url)
        context?.startActivity(openURL)
    }

    inner class PlaceHolderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var hide_btn = itemView.findViewById<ImageView>(R.id.placeholder_hide)
    }
}