package com.example.bookreviewsver112.src.Main.Recommend

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver112.R
import com.example.bookreviewsver112.src.Main.home.`interface`.Constants
import com.example.bookreviewsver112.src.Main.home.`interface`.Constants.TAG
import com.example.bookreviewsver112.src.Main.home.model.BestSellerData
import java.security.AccessController.getContext


class FavoriteBookViewHolder (itemView: View): RecyclerView.ViewHolder(itemView){
    //뷰들 가져옴
    private val bookRank = itemView.findViewById<TextView>(R.id.tv_favorite_book_title)
    private val bookImgView = itemView.findViewById<ImageView>(R.id.iv_favorite_book_img)



    //뷰와 데이터를 bind 묶음
    fun bindWithView(BestSellerItem: BestSellerData){
        //best seller 이미지 설정 by url
        //context 대신 MyApplication.instance 로 전역으로 가져옴
//        Glide.with(MyApplication.instance)
//            .load(BestSellerItem.coverImgUrl)
//            .placeholder(R.drawable.ic_baseline_photo_24)
//            .into(bookImgView)

        // 나머지 정보들 넣기
//        bookRank.text = BestSellerItem.rank.toString()
//        bookTitle.text = BestSellerItem.title
//
    }


}