package com.example.bookreviewsver.src.Main.Book

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.core.view.size
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentCbChatListBinding
import com.example.bookreviewsver.src.Main.Chat.ChatActivity

class CBChatFragment : BaseFragment<FragmentCbChatListBinding>(FragmentCbChatListBinding::bind, R.layout.fragment_cb_chat_list){
    //어댑터
    private lateinit var chatListAdapter: BookDetailChatListAdapter
    private var recyclerView: RecyclerView? = null
    var bookId :Int? = 0
    var bookImgUrl :String? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        val mSwipeRefreshLayout= binding.swipeLayout2
        mSwipeRefreshLayout.setOnRefreshListener {
            recyclerView = view.findViewById<View>(R.id.rv_book_detail_chat_list) as RecyclerView
            this.chatListAdapter = BookDetailChatListAdapter()
            recyclerView!!.adapter = this.chatListAdapter
            recyclerView!!.layoutManager = LinearLayoutManager(activity)

            mSwipeRefreshLayout.isRefreshing = false
        }
        recyclerView = view.findViewById<View>(R.id.rv_book_detail_chat_list) as RecyclerView
        this.chatListAdapter = BookDetailChatListAdapter()
        recyclerView!!.adapter = this.chatListAdapter
        recyclerView!!.layoutManager = LinearLayoutManager(activity)

        bookImgUrl = MyApplication.sSharedPreferences.getString("bookImgUrl", null)
        bookId = MyApplication.sSharedPreferences.getInt("bookId", 0)

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.book_chat_tb_items, menu)
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id. menu_new_chat_room-> {
                val builder = AlertDialog.Builder(requireContext())
                val dialogView = layoutInflater.inflate(R.layout.post_chat_dialog_layout, null)
                val dialogText = dialogView.findViewById<EditText>(R.id.dialogEt)

                builder.setView(dialogView)
                        .setPositiveButton("확인") { dialogInterface, i ->

                            var chatRoomTitle = dialogText.text.toString()
                            MyApplication.sSharedPreferences.edit().putString("chatRoomTitle", chatRoomTitle).apply()
                            //chatActivity 로 전환 // 채팅방 명 정해서 넘기기
                            val intent = Intent(activity, ChatActivity::class.java)
                            intent.putExtra("bookId", bookId)
                            intent.putExtra("bookImgUrl", bookImgUrl)
                            startActivity(intent)

                        }

                        .setNegativeButton("취소") { dialogInterface, i ->
                            /* 취소일 때 아무 액션이 없으므로 빈칸 */
                        }
                        .show()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }


}
