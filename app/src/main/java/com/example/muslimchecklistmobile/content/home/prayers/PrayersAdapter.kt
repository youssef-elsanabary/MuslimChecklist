package com.example.muslimchecklistmobile.content.home.prayers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.muslimchecklistmobile.R

class PrayersAdapter(private val prayersAdapterClickListener: PrayersAdapterClickListener) : RecyclerView.Adapter<PrayersAdapter.ViewHolder>() {

     var data = arrayOf(R.drawable.isha,R.drawable.maghrib,R.drawable.aser,R.drawable.duhr,R.drawable.duha,R.drawable.fajr)

    interface PrayersAdapterClickListener{
        fun onItemClick(position :Int)
    }
    inner class ViewHolder (itemView :View) :RecyclerView.ViewHolder(itemView) {
        var itemImage = itemView.findViewById<ImageView>(R.id.prayers_image)!!
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.prayers_list_item,parent,false)
        return ViewHolder(v)
    }
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemImage.setImageResource(data[position])
        holder.itemImage.setOnClickListener {prayersAdapterClickListener.onItemClick(position)}
    }
    override fun getItemCount(): Int {
        return data.size
    }
}