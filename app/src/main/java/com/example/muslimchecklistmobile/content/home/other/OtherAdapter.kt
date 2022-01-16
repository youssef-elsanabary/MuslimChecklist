package com.example.muslimchecklistmobile.content.home.other

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muslimchecklistmobile.R
class OtherAdapter(private val otherAdapterClickListener:OtherAdapterClickListener ) : RecyclerView.Adapter<OtherAdapter.ViewHolder>() {

    interface OtherAdapterClickListener{
        fun onOtherClick(position: Int)
    }

    private var img = arrayOf( R.drawable.fasting, R.drawable.ablution,R.drawable.quran)
    var text = arrayOf("الصيام","الوضوء قبل النوم","االقرءان")

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        var item_Img = itemView.findViewById<ImageView>(R.id.other_img)!!
        var item_Txt = itemView.findViewById<TextView>(R.id.other_text)!!
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.other_list_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.item_Img.setImageResource(img[position])
        holder.item_Txt.text = text[position]
        holder.item_Img.setOnClickListener {otherAdapterClickListener.onOtherClick(position)}
    }

    override fun getItemCount(): Int {
        return 3
    }

}