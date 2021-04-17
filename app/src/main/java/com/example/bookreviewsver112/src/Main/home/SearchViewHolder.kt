package com.example.bookreviewsver112.src.Main.home

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.application.config.MyApplication.Companion.instance
import com.example.bookreviewsver112.R
import com.example.bookreviewsver112.src.Main.home.`interface`.Constants
import com.example.bookreviewsver112.src.Main.home.model.BestSellerData
import com.example.bookreviewsver112.src.Main.home.model.SearchData
import com.example.bookreviewsver112.src.report.ReportWriterActivity
import java.security.AccessController.getContext

class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val bookImgView = itemView.findViewById<ImageView>(R.id.iv_search_book_img)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_search_book_title)
    private val bookAuthor = itemView.findViewById<TextView>(R.id.tv_search_book_author)
    private val bookPublisher = itemView.findViewById<TextView>(R.id.tv_search_book_publisher)
//    private val bookDescription = itemView.findViewById<TextView>(R.id.tv_search_description)
    private val bookPubDate = itemView.findViewById<TextView>(R.id.tv_search_book_pubdate)
//    private val bookAdd = itemView.findViewById<TextView>(R.id.iv_add)
    private val bookLikeFalse = itemView.findViewById<ImageView>(R.id.iv_search_favorite_false)
    private val bookLikeTrue = itemView.findViewById<ImageView>(R.id.iv_search_favorite_true)

    private val SearchCardView = itemView.findViewById<CardView>(R.id.cardview_search_book_item)

    //뷰와 데이터를 bind 묶음
    fun bindWithView(SearchItem: SearchData) {
        Log.d(Constants.TAG, "SearchViewHolder- bindWithView() called")

        //best seller 이미지 설정 by url
        //context 대신 MyApplication.instance 로 전역으로 가져옴
        Glide.with(MyApplication.instance)
                .load(SearchItem.coverImgUrl)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(bookImgView)

        // 나머지 정보들 넣기
        bookTitle.text = SearchItem.title
        bookAuthor.text = SearchItem.author
        bookPublisher.text = SearchItem.publisher
        //    bookDescription.text = SearchItem.description
        bookPubDate.text = SearchItem.pubDate


        bookLikeFalse.setOnClickListener(View.OnClickListener {
            SearchItem.favorite = true
            bookLikeFalse.visibility = View.INVISIBLE
            bookLikeTrue.visibility = View.VISIBLE
        })


        bookLikeTrue.setOnClickListener(View.OnClickListener {
            SearchItem.favorite = false
            bookLikeTrue.visibility = View.INVISIBLE
            bookLikeFalse.visibility = View.VISIBLE
        })


    }
}