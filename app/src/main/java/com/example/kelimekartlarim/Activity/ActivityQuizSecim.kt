package com.example.kelimekartlarim.Activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.kelimekartlarim.R
import com.example.kelimekartlarim.RecyclerViewAdapter.Adapter
import com.example.kelimekartlarim.databinding.ActivityQuizSecimBinding

class ActivityQuizSecim : AppCompatActivity() {
    lateinit var binding: ActivityQuizSecimBinding
    var tableNames = arrayListOf("Hayvanlar", "Kıyafetler", "Fiiller", "İsimler")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityQuizSecimBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = Adapter(tableNames, this::rvOnItemClick)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvYapi.adapter = adapter
        binding.rvYapi.layoutManager = layoutManager
        binding.rvYapi.addItemDecoration(DividerItemDecoration(this, layoutManager.orientation))

    }

    private fun rvOnItemClick(position: Int) {
        val selectedTable = tableNames[position]
        val intent = Intent(this, ActivityQuiz::class.java)
        intent.putExtra("tableName", selectedTable) // veri taşıma işlemi varsa buraya
        startActivity(intent)
    }
}