package com.example.kelimekartlarim.CardStackAdapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.kelimekartlarim.R

class CardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    var engKelime : TextView = itemView.findViewById(R.id.englishText)
    var trKelime : TextView = itemView.findViewById(R.id.turkishText)
}
