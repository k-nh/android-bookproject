package com.example.bookreviewsver112.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.R

class HomeActivity : AppCompatActivity(){
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        setSupportActionBar(findViewById(R.id.tb_home))

        getSupportActionBar()!!.setDisplayHomeAsUpEnabled(true);
        getSupportActionBar()!!.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24);
        getSupportActionBar()!!.setTitle("");

        /*
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        var searchView: SearchView = findViewById(R.id.search_view) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
         */
    }

}
