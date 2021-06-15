package com.example.bookreviewsver.src.Main.MyPage

import android.app.Activity
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Book.BookDetailActivity
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResult


class LikeListAdapter: RecyclerView.Adapter<LikeListViewHolder>() {

    private var LikeList = ArrayList<LikeListResult>()

    //viewholder와 item 레이아웃 뷰를 연결하는 역할 메소드 by LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeListViewHolder {
        return LikeListViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_favorite_book,parent,false))
    }

    //전체 아이템 개수
    override fun getItemCount(): Int {
        return this.LikeList.size
    }

    fun submitLikeList(likeList: ArrayList<LikeListResult>){
        this.LikeList = likeList
    }

    override fun onBindViewHolder(holder: LikeListViewHolder, position: Int) {
        holder.bindWithView(this.LikeList[position])
    }

}

class LikeListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val FavoriteCardView = itemView.findViewById<CardView>(R.id.cardview_favorite_item)
    private val bookImgView = itemView.findViewById<ImageView>(R.id.iv_favorite_book_img)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_favorite_book_title)
    private val promise = itemView.findViewById<TextView>(R.id.tv_save_book)
    private val context = itemView.context



    //뷰와 데이터를 bind 묶음
    fun bindWithView(Item: LikeListResult) {
        Log.d(Constants.TAG, "LikeListViewHolder- bindWithView() called")


        //이미지 설정 by url
        //context 대신 MyApplication.instance 로 전역으로 가져옴
        Glide.with(MyApplication.instance)
                .load(Item.bookImgUrl)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(bookImgView)

        // 나머지 정보들 넣기
        bookTitle.text = Item.bookName
        if(Item.promise == "null"){
            promise.text = ""
        }else {
            promise.text = Item.promise
        }

        FavoriteCardView.setOnClickListener{
            Log.d(Constants.TAG,"LikeList- click() called")
            Intent(context, BookDetailActivity::class.java).apply {
                MyApplication.sSharedPreferences.edit().putInt("bookId", Item.bookID!!).apply()
                MyApplication.sSharedPreferences.edit().putString("isbn", Item.isbn).apply()
            }.run { context.startActivity(this) }

        }
    }
}
