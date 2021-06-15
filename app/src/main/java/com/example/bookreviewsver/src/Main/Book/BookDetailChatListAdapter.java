package com.example.bookreviewsver.src.Main.Book;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.application.config.MyApplication;
import com.example.bookreviewsver.R;
import com.example.bookreviewsver.src.Main.Chat.ChatActivity;
import com.example.bookreviewsver.src.Main.Chat.ChatModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;
import java.util.TreeMap;

public class BookDetailChatListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    private List<ChatModel> chatModelList = new ArrayList<>();
    private ArrayList<String> destinationBookTitles = new ArrayList<String>();
    private ArrayList<Integer> destinationBookIds = new ArrayList<Integer>();
    private ArrayList<String> destinationBookImages = new ArrayList<String>();
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    public int bookId = MyApplication.sSharedPreferences.getInt("bookId", 0);

    public BookDetailChatListAdapter() {
        //해당 책의 채팅방
        Log.d("채팅방",bookId+"");
        Log.d("채팅방",FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("bookId/").equalTo(bookId)+"");

        FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("bookId/").equalTo(bookId).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatModelList.clear();
                for(DataSnapshot item : snapshot.getChildren()){
                    chatModelList.add(item.getValue(ChatModel.class));
                }
                notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_chat_list,parent,false);
        return new ChatListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ChatListViewHolder chatListViewHolder = (ChatListViewHolder)holder;

        //현재 채팅방의 bookid, title, image 받아옴
        destinationBookIds.add(chatModelList.get(position).bookId);
        destinationBookTitles.add(chatModelList.get(position).ChatTitle);
        destinationBookImages.add(chatModelList.get(position).bookImgUrl);
        Log.d("chatlistap",chatModelList.get(position).bookImgUrl+"");

            // 해당하는 bookimg, chattitle을 출력해야함
        chatListViewHolder.chatRoomTitle.setText(destinationBookTitles.get(position));
        Glide.with(chatListViewHolder.itemView.getContext())
                .load(destinationBookImages.get(position))
                .apply(new RequestOptions().circleCrop())
                .into(chatListViewHolder.chatRoomImg);


        //내림차순 정렬 후 마지막 메세지의 키값을 가져옴
        Map<String,ChatModel.Comment> commentMap = new TreeMap<>(Collections.reverseOrder());
        commentMap.putAll(chatModelList.get(position).comments);

        if(commentMap.keySet().toArray().length>0) {
            String lastMessageKey = (String) commentMap.keySet().toArray()[0];
            chatListViewHolder.chatRoomLastMessage.setText(chatModelList.get(position).comments.get(lastMessageKey).message);

            chatListViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ChatActivity.class);
                    Log.d("destinationBookTitles",destinationBookTitles.get(position)+"확인");
                    intent.putExtra("chatRoomTitle", destinationBookTitles.get(position));
                    //ActivityOptions activityOptions = ActivityOptions.makeCustomAnimation(v.getContext(),R.anim.)
                    v.getContext().startActivity(intent);
                }
            });
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            long unixTime = (long) chatModelList.get(position).comments.get(lastMessageKey).timestamp;
            Date date = new Date(unixTime);
            String time = simpleDateFormat.format(date);
            chatListViewHolder.chatRoomLastTime.setText(time);

        }

    }

    @Override
    public int getItemCount() {
        return chatModelList.size();
    }

    private class ChatListViewHolder extends RecyclerView.ViewHolder {
        public ImageView chatRoomImg;
        public TextView chatRoomTitle;
        public TextView chatRoomLastMessage;
        public TextView chatRoomLastTime;

        public ChatListViewHolder(View view) {
            super(view);
            chatRoomImg = view.findViewById(R.id.iv_chat_book_img);
            chatRoomTitle = view.findViewById(R.id.tv_chat_title);
            chatRoomLastMessage = view.findViewById(R.id.tv_chat_last_msg);
            chatRoomLastTime = view.findViewById(R.id.tv_last_time);

        }
    }
}
