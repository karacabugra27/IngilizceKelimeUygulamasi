package com.example.kelimekartlarim.RecyclerViewAdapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kelimekartlarim.R

class ViewHolder(itemView: View, itemClick: (position: Int) -> Unit) : RecyclerView.ViewHolder(itemView) {
    var kategoriAd: TextView

    init {
        kategoriAd = itemView.findViewById(R.id.kategoriAd)

        itemView.setOnClickListener { view ->
            itemClick(adapterPosition)
        }
    }


}