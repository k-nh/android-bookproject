package com.example.bookreviewsver112.src.Main.home

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver112.R
import com.example.bookreviewsver112.src.Main.home.model.BestSellerData
import com.example.bookreviewsver112.src.Main.home.model.SearchData

class SearchAdapter: RecyclerView.Adapter<SearchViewHolder>() {

    private var SearchBookList = ArrayList<SearchData>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val searchViewHolder = SearchViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_search_book,parent,false))
        return searchViewHolder    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindWithView(this.SearchBookList[position])

    }

    override fun getItemCount(): Int {
        return this.SearchBookList.size
    }

    fun submitList(SearchBookList: ArrayList<SearchData>){
        this.SearchBookList = SearchBookList
    }
}