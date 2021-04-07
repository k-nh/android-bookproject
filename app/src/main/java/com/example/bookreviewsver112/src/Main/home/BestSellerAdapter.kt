package com.example.bookreviewsver112.src.Main.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver112.R
import com.example.bookreviewsver112.src.Main.home.model.BestSellerData

class BestSellerAdapter: RecyclerView.Adapter<BestSellerViewHolder>(){
    private var BestSellerList = ArrayList<BestSellerData>()

    //뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val bestSellerViewHolder = BestSellerViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_best_seller,parent,false))
        return bestSellerViewHolder
    }


    //뷰가 bind 됐을때 데이터를 뷰 홀더에 넘겨준다
    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        holder.bindWithView(this.BestSellerList[position])

    }

    //보여줄 목록 개수
    override fun getItemCount(): Int {
        return this.BestSellerList.size
    }

    //외부에서 어댑터에 데이터 배열을 넣어준다
    //class BestSellerAdapter(BestSellerList: ArrayList<BestSellerData>) : RecyclerView.Adapter<BestSellerViewHolder>(){
    //이런식으로 클래스 명에 넣어줄 수도 있음
    fun submitList(BestSellerList: ArrayList<BestSellerData>){
        this.BestSellerList = BestSellerList
    }


}