package com.example.bookreviewsver.src.Main.MyPage

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Home.model.LikeResponse
import com.example.bookreviewsver.src.Main.Home.model.SearchData
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.LikeResult

class LikeListAdapter: RecyclerView.Adapter<LikeListViewHolder>() {

    private var LikeList = ArrayList<LikeResult>()


    //viewholder와 item 레이아웃 뷰를 연결하는 역할 메소드 by LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeListViewHolder {
        return LikeListViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_favorite_book,parent,false))
    }

    //실제 데이터와 viewholder 연결
    override fun onBindViewHolder(holder: LikeListViewHolder, position: Int) {
        holder.bindWithView(this.LikeList[position])

    }

    //전체 아이템 개수
    override fun getItemCount(): Int {
        return this.LikeList.size
    }

    fun submitList(likeList: ArrayList<LikeResult>){
        this.LikeList = likeList
    }
}