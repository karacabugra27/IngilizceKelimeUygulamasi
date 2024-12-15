package com.example.kelimekartlarim.CardStackAdapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kelimekartlarim.Database.Kelime
import com.example.kelimekartlarim.R

class CardAdapter(private val kelimeListesi: List<Kelime>) : RecyclerView.Adapter<CardViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.kart_layout, parent, false)
        return CardViewHolder(view)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val kelime = kelimeListesi[position]
        holder.engKelime.text = kelime.AdEng
        holder.trKelime.text = kelime.AdTurkce
        holder.trKelime.visibility = View.GONE

        holder.itemView.setOnClickListener {
            if (holder.trKelime.visibility == View.GONE) {
                holder.trKelime.visibility = View.VISIBLE
                holder.engKelime.visibility = View.GONE
            } else {
                holder.trKelime.visibility = View.GONE
                holder.engKelime.visibility = View.VISIBLE
            }
        }
    }

    override fun getItemCount(): Int {
        return kelimeListesi.size
    }
}

