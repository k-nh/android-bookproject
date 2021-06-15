package com.example.bookreviewsver.src.Main.MyPage

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
import com.example.bookreviewsver.src.Main.MyPage.model.ReadingListResult

class ReadingListAdapter: RecyclerView.Adapter<ReadingListViewHolder>() {
    
    private var ReadingList = ArrayList<ReadingListResult>()
    
    //viewholder와 item 레이아웃 뷰를 연결하는 역할 메소드 by LayoutInflater
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReadingListViewHolder {
        return ReadingListViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_favorite_book,parent,false))
    }

    //실제 데이터와 viewholder 연결
    override fun onBindViewHolder(holder: ReadingListViewHolder, position: Int) {
        holder.bindWithView(this.ReadingList[position])

    }

    //전체 아이템 개수
    override fun getItemCount(): Int {
        return this.ReadingList.size
    }

    fun submitReadingList(readingList: ArrayList<ReadingListResult>){
        this.ReadingList = readingList
    }
}

class ReadingListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val FavoriteCardView = itemView.findViewById<CardView>(R.id.cardview_favorite_item)
    private val bookImgView = itemView.findViewById<ImageView>(R.id.iv_favorite_book_img)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_favorite_book_title)
    private val context = itemView.context
    private val startDay = itemView.findViewById<TextView>(R.id.tv_save_book)



    //뷰와 데이터를 bind 묶음
    fun bindWithView(Item: ReadingListResult) {
        Log.d(Constants.TAG, "LikeListViewHolder- bindWithView() called")


        //이미지 설정 by url
        //context 대신 MyApplication.instance 로 전역으로 가져옴
        Glide.with(MyApplication.instance)
                .load(Item.bookImgUrl)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(bookImgView)

        // 나머지 정보들 넣기
        bookTitle.text = Item.bookName
        startDay.text = Item.startDate


        FavoriteCardView.setOnClickListener{
            Log.d(Constants.TAG,"searchViewHolder- click() called")
            Intent(context, BookDetailActivity::class.java).apply {
                MyApplication.sSharedPreferences.edit().putInt("bookId", Item.bookID!!)
                    .apply()
                MyApplication.sSharedPreferences.edit().putString("isbn", Item.isbn).apply()

            }.run { context.startActivity(this) }
        }

    }
}
