package com.example.bookreviewsver.src.Main.Chat

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver.R

class ChatListAdapter: RecyclerView.Adapter<ChatViewHolder>(){
    private var chatList = ArrayList<ChatItem>()

    //뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val chatViewHolder = ChatViewHolder(
                LayoutInflater.from(parent.context).inflate(
                        R.layout.item_chat_list,parent,false)
        )
        return chatViewHolder
    }


    //뷰가 bind 됐을때 데이터를 뷰 홀더에 넘겨준다
    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bindWithView(this.chatList[position])

    }

    //보여줄 목록 개수
    override fun getItemCount(): Int {
        return this.chatList.size
    }

    //외부에서 어댑터에 데이터 배열을 넣어준다
    //class BestSellerAdapter(BestSellerList: ArrayList<BestSellerData>) : RecyclerView.Adapter<BestSellerViewHolder>(){
    //이런식으로 클래스 명에 넣어줄 수도 있음
    fun submitList(chatList: ArrayList<ChatItem>){
        this.chatList = chatList
    }

}