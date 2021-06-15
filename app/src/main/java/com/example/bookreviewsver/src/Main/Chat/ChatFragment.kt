package com.example.bookreviewsver.src.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.config.MyApplication
import com.example.bookreviewsver.MainActivity
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentChatListBinding
import com.example.bookreviewsver.src.Main.Chat.ChatListAdapter
import com.example.bookreviewsver.src.Main.Report.service.ReportService

class ChatFragment : BaseFragment<FragmentChatListBinding>(FragmentChatListBinding::bind, R.layout.fragment_chat_list){
    //어댑터
    private lateinit var chatListAdapter : ChatListAdapter
    private var mRecyclerView: RecyclerView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mSwipeRefreshLayout= binding.lySwipe
        mSwipeRefreshLayout.setOnRefreshListener {
            mRecyclerView = view.findViewById<View>(R.id.rv_chat_list) as RecyclerView
            this.chatListAdapter = ChatListAdapter()
            mRecyclerView!!.adapter = this.chatListAdapter
            mRecyclerView!!.layoutManager = LinearLayoutManager(activity)

            mSwipeRefreshLayout.isRefreshing = false
        }
        mRecyclerView = view.findViewById<View>(R.id.rv_chat_list) as RecyclerView
        this.chatListAdapter = ChatListAdapter()
        mRecyclerView!!.adapter = this.chatListAdapter
        mRecyclerView!!.layoutManager = LinearLayoutManager(activity)

        (activity as MainActivity).supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        (activity as MainActivity).supportActionBar?.setCustomView(R.layout.toolbar_chat)

        val bookImg = MyApplication.sSharedPreferences.getString("bookImgUrl", null)
        val bookTitle = MyApplication.sSharedPreferences.getString("bookTitle", null)

    }


}
