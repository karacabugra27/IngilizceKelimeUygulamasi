package com.example.kelimekartlarim.RecyclerViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.kelimekartlarim.Database.Kelime
import com.example.kelimekartlarim.R
//allist veri kaynağımızla recyclerviewi uyumlu hale getiririz, bağlama yaparız.
class Adapter(val alList: ArrayList<String>, itemClick: (position: Int) -> Unit) : RecyclerView.Adapter<ViewHolder>() {
    val itemClick = itemClick

    //her recyclerview ögesi için view oluştururuz.
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(
            R.layout.list_item, parent, false
        )
        return ViewHolder(v, itemClick)
    }

    override fun getItemCount(): Int {
        return alList.size
    }

    //listedeki verileri recyclerview ögelerine bağlarız.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val tableName = alList.get(position)
        holder.kategoriAd.text = tableName
    }

}