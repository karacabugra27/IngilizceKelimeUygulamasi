package com.example.kelimekartlarim

import android.app.Dialog
import android.widget.LinearLayout
import androidx.recyclerview.widget.LinearLayoutManager

fun Dialog.setupDialog(layoutResId : Int){
    setContentView(layoutResId)
    window!!.setLayout(
        LinearLayout.LayoutParams.MATCH_PARENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    )
    setCancelable(false)
}