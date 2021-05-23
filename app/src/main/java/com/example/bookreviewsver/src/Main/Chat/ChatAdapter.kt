package com.example.bookreviewsver.src.Main.Chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import de.hdodenhof.circleimageview.CircleImageView


class ChatAdapter(var messageItems: ArrayList<MessageItem>, var layoutInflater: LayoutInflater) :
    BaseAdapter() {
    var userId: String? = MyApplication.sSharedPreferences.getString("userId", null)

    override fun getCount(): Int {
        return messageItems.size
    }

    override fun getItem(position: Int): Any {
        return messageItems[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, view: View?, viewGroup: ViewGroup?): View? {

        //현재 보여줄 번째의(position)의 데이터로 뷰를 생성
        val item = messageItems[position]

        //재활용할 뷰는 사용하지 않음!!
        var itemView: View? = null

        //메세지가 내 메세지인지??
        if (item.name == userId) {
            itemView = layoutInflater.inflate(R.layout.my_msgbox, viewGroup, false)
        } else {
            itemView = layoutInflater.inflate(R.layout.other_msgbox, viewGroup, false)
        }

        //만들어진 itemView에 값들 설정
        val iv: CircleImageView = itemView.findViewById(R.id.iv)
        val tvName: TextView = itemView.findViewById(R.id.tv_name)
        val tvMsg: TextView = itemView.findViewById(R.id.tv_msg)
        val tvTime: TextView = itemView.findViewById(R.id.tv_time)
        tvName.text = item.name
        tvMsg.text = item.message
        tvTime.text = item.time
        Glide.with(itemView).load(item.profileUrl).into(iv)
        return itemView
    }
}