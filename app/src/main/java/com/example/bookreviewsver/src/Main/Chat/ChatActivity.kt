package com.example.bookreviewsver.src.Main.Chat

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ListView
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.config.BaseActivity
import com.example.bookreviewsver.databinding.ActivityChatBinding
import com.google.firebase.database.*
import io.reactivex.internal.util.NotificationLite.getValue
import java.util.*
import kotlin.collections.ArrayList


class ChatActivity : BaseActivity<ActivityChatBinding>(ActivityChatBinding::inflate) {
    var etMessage: EditText? = null
    var listView: ListView? = null
    var messageItems: ArrayList<MessageItem> = ArrayList()
    var adapter: ChatAdapter? = null


    //Firebase Database 관리 객체참조변수
    var firebaseDatabase: FirebaseDatabase? = null

    //'chat'노드의 참조객체 참조변수
    var chatRef: DatabaseReference? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val title = intent.getStringExtra("title")

        //제목줄 제목글시를 닉네임으로(또는 채팅방)
        supportActionBar!!.setTitle(title)
        etMessage = binding.etMessage
        listView = binding.listview
        adapter = ChatAdapter(messageItems, layoutInflater)
        listView!!.setAdapter(adapter)


        //Firebase DB관리 객체와 'chat'노드 참조객체 얻어오기
        firebaseDatabase = FirebaseDatabase.getInstance()
        chatRef = firebaseDatabase!!.getReference("chat")

        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        //firebaseDB에서 채팅 메세지들 실시간 읽어오기..
        //'chat'노드에 저장되어 있는 데이터들을 읽어오기
        //chatRef에 데이터가 변경되는 것으 듣는 리스너 추가
        chatRef!!.addChildEventListener(object : ChildEventListener {
            //새로 추가된 것만 줌 ValueListener는 하나의 값만 바뀌어도 처음부터 다시 값을 줌
            override fun onChildAdded(@NonNull dataSnapshot: DataSnapshot, @Nullable s: String?) {

                //새로 추가된 데이터(값 : MessageItem객체) 가져오기
                val messageItem = dataSnapshot.getValue(MessageItem::class.java)!!

                //새로운 메세지를 리스뷰에 추가하기 위해 ArrayList에 추가
                messageItems.add(messageItem)

                //리스트뷰를 갱신
                adapter!!.notifyDataSetChanged()
                listView!!.setSelection(messageItems.size - 1) //리스트뷰의 마지막 위치로 스크롤 위치 이동
            }

            override fun onChildChanged(
                @NonNull dataSnapshot: DataSnapshot,
                @Nullable s: String?
            ) {
            }

            override fun onChildRemoved(@NonNull dataSnapshot: DataSnapshot) {}
            override fun onChildMoved(@NonNull dataSnapshot: DataSnapshot, @Nullable s: String?) {}
            override fun onCancelled(@NonNull databaseError: DatabaseError) {}
        })
    }

    fun clickSend(view: View?) {

        //firebase DB에 저장할 값들( 닉네임, 메세지, 프로필 이미지URL, 시간)
        val nickName: String? = User.nickName
        val message = etMessage!!.text.toString()
        val profileUrl: String? = User.profileUrl

        //메세지 작성 시간 문자열로..
        val calendar: Calendar = Calendar.getInstance() //현재 시간을 가지고 있는 객체
        val time: String = calendar.get(Calendar.HOUR_OF_DAY).toString() + ":" + calendar.get(
            Calendar.MINUTE
        ) //14:16

        //firebase DB에 저장할 값() 설정
        val messageItem = MessageItem(nickName, message, time, profileUrl)
        //'char'노드에 MessageItem객체를 통해
        chatRef!!.push().setValue(messageItem)

        //EditText에 있는 글씨 지우기
        etMessage!!.setText("")

        //소프트키패드를 안보이도록..
        val imm: InputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)

        //처음 시작할때 EditText가 다른 뷰들보다 우선시 되어 포커스를 받아 버림.
        //즉, 시작부터 소프트 키패드가 올라와 있음.

        //그게 싫으면...다른 뷰가 포커스를 가지도록
        //즉, EditText를 감싼 Layout에게 포커스를 가지도록 속성을 추가!![[XML에]
    }
}


