package com.example.bookreviewsver.src.Main.Chat

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.model.BestSellerData
import kotlinx.android.synthetic.main.item_chat_list.view.*

class ChatViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val chatList = itemView.findViewById<LinearLayout>(R.id.ly_chat_list)
    private val bookImage = itemView.findViewById<ImageView>(R.id.iv_chat_book_img)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_chat_book_title)

    //뷰와 데이터를 bind 묶음
    fun bindWithView(chatItem: ChatItem) {
        Log.d(Constants.TAG, "ViewHolder- bindWithView() called")
    }

}