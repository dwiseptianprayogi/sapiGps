package com.example.sapigps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DatabaseReference

class ListActivity : AppCompatActivity() {

    private lateinit var dbRef:DatabaseReference
    private lateinit var sapiRecycleView:RecyclerView
    private lateinit var sapiArray: ArrayList<Sapi>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)

        sapiRecycleView = findViewById(R.id.listSapi)
        sapiRecycleView.layoutManager = LinearLayoutManager(this)

    }
}