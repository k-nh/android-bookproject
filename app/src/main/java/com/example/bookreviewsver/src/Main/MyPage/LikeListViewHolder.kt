package com.example.bookreviewsver.src.Main.MyPage

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Home.BookDetailActivity
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.model.SearchData
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse
import com.example.bookreviewsver.src.Main.MyPage.model.LikeResult
import com.google.gson.annotations.SerializedName

class LikeListViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val FavoriteCardView = itemView.findViewById<CardView>(R.id.cardview_favorite_item)
    private val bookImgView = itemView.findViewById<ImageView>(R.id.iv_favorite_book_img)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_favorite_book_title)
    private val context = itemView.context



    //뷰와 데이터를 bind 묶음
    fun bindWithView(favoriteItem: LikeResult) {
        Log.d(Constants.TAG, "LikeListViewHolder- bindWithView() called")


        //이미지 설정 by url
        //context 대신 MyApplication.instance 로 전역으로 가져옴
        Glide.with(MyApplication.instance)
                .load(favoriteItem.bookImgUrl)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(bookImgView)

        // 나머지 정보들 넣기
        bookTitle.text = favoriteItem.bookName

        FavoriteCardView.setOnClickListener{
            Log.d(Constants.TAG,"searchViewHolder- click() called")
//            val bookImgUrl : String,
//            val bookID: Int,
//            @SerializedName("book_name")
//            val bookName: String,
//            val favoriteId: Int,
//            val categoryID: Int,
//            val id: String,
//            val regdate: String
//            Intent(context, BookDetailActivity::class.java).apply {
//                putExtra("bookId",favoriteItem.bookID)
//                putExtra("title",favoriteItem.title)
//                putExtra("author",favoriteItem.author)
//                putExtra("publisher",favoriteItem.publisher)
//                putExtra("description",favoriteItem.description)
//                putExtra("coverImgUrl",favoriteItem.coverImgUrl)
//                putExtra("pubDate",favoriteItem.pubDate)
//            }.run { context.startActivity(this) }
        }

    }
}