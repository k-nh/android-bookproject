package com.example.bookreviewsver.src.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentChatlistBinding
import com.example.bookreviewsver.src.Main.Chat.ChatItem
import com.example.bookreviewsver.src.Main.Chat.ChatListAdapter
import com.example.bookreviewsver.src.Main.Home.BestSellerAdapter
import com.example.bookreviewsver.src.Main.Home.SearchAdapter
import com.example.bookreviewsver.src.Main.Report.model.Result

class ChatFragment : BaseFragment<FragmentChatlistBinding>(FragmentChatlistBinding::bind, R.layout.fragment_chatlist){
    //어댑터
    private lateinit var chatListAdapter : ChatListAdapter
    private var mRecyclerView: RecyclerView? = null
    private var chatArrayList = ArrayList<ChatItem>()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mRecyclerView = view.findViewById<View>(R.id.rv_chat_list) as RecyclerView

//        val title = intent.getStringExtra("title")
//        val coverImgUrl = intent.getStringExtra("coverImgUrl")
//
//        Glide.with(this)
//                .load(coverImgUrl)
//                .into(binding.ivChatBookImg)
//
//        binding.tvChatBookTitle.text = title

        val bookTitle = arguments?.getString("bookTitle")
        val bookImgUrl = arguments?.getString("bookImgUrl")

        this.chatListAdapter = ChatListAdapter()
        var chat = ChatItem(bookImgUrl,bookTitle)
        chatArrayList.add(chat)
        if (chatArrayList != null) {
            this.chatListAdapter.submitList(chatArrayList)
        }

        mRecyclerView!!.layoutManager = GridLayoutManager(activity, 1, GridLayoutManager.VERTICAL, false)
        mRecyclerView!!.adapter = this.chatListAdapter

    }

}