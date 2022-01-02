package com.example.muslimchecklistmobile.content.home.supplications

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.muslimchecklistmobile.R
import com.example.muslimchecklistmobile.content.home.prayers.PrayersAdapter

class SupplicationAdapter(private val supplicationAdapter: SupplicationAdapter.SupplicationAdapterClickListener) : RecyclerView.Adapter<SupplicationAdapter.ViewHolder>() {


    interface SupplicationAdapterClickListener{
        fun onSupplicationClick(position: Int)
    }

    var suppimg = arrayOf(R.drawable.sleep,R.drawable.evening,R.drawable.morning)
    var suppText = arrayOf("اذكار النوم","اذكار المساء","اذكار الصباح")

    class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView) {
        var itemImg = itemView.findViewById<ImageView>(R.id.supp_img)
        var itemTxt = itemView.findViewById<TextView>(R.id.supp_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.supplications_list_item,parent,false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemImg.setImageResource(suppimg[position])
        holder.itemTxt.text = suppText[position]
        holder.itemImg.setOnClickListener{supplicationAdapter.onSupplicationClick(position)}
    }

    override fun getItemCount(): Int {
        return 3
    }

}