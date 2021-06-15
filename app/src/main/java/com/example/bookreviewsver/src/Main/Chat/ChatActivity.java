package com.example.bookreviewsver.src.Main.Chat;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.application.config.MyApplication;
import com.example.bookreviewsver.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ServerValue;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class ChatActivity extends AppCompatActivity {
    private int bookId;
    public String bookImgUrl;
    public ArrayList<String> existChatTitles = new ArrayList<>();
    private Button btnSend;
    public ImageButton btnChatSTT;
    private EditText etSendMessage;
    private String userProfileImg = MyApplication.sSharedPreferences.getString("userProfileImg",null);
    private String userId = MyApplication.sSharedPreferences.getString("userId", null);
    private String newChatRoomTitle = MyApplication.sSharedPreferences.getString("chatRoomTitle", null);
    private String existChatRoomTitle;
    private String chatRoomId = null;
    private RecyclerView recyclerView;
    private SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd HH:mm");
    private ChatModel chatModel;
    public SpeechRecognizer mRecognizer;
    public Intent intent;
    final int PERMISSION = 1;
    private UserModel destinationUserModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        existChatRoomTitle = getIntent().getStringExtra("chatRoomTitle");
        Log.d("타이틀",existChatRoomTitle+"이야");
        bookId = getIntent().getIntExtra("bookId",0);
        bookImgUrl = getIntent().getStringExtra("bookImgUrl");
        btnSend = (Button)findViewById(R.id.btn_send);
        btnChatSTT = (ImageButton)findViewById(R.id.btn_chat_mic);
        etSendMessage = (EditText)findViewById(R.id.et_message);
        recyclerView = (RecyclerView)findViewById(R.id.rv_chat_item);
        setSupportActionBar(findViewById(R.id.tb_chat));
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        checkChatRoom();

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //넣을 chatmodel 생성
                ChatModel chatModel = new ChatModel();
                chatModel.bookId = bookId;
                chatModel.users.put(userId,true);
                chatModel.ChatTitle = newChatRoomTitle;
                chatModel.bookImgUrl = bookImgUrl;

                //이전에 존재하는 채팅방이 없을경우 새로 생성
                if(chatRoomId == null){
                    //서버에 들어간것 확인전엔 버튼 못누르게 // 중복방지
                    btnSend.setEnabled(false);
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").push().setValue(chatModel).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            existChatRoomTitle = newChatRoomTitle;
                            checkChatRoom();
                        }
                    });
                }else{
                    //이미 있을경우 그에 해당하는 채팅방에 넣어야함
                    //comment 생성
                    ChatModel.Comment comment = new ChatModel.Comment();
                    comment.userId = userId;
                    comment.userProfileImg = userProfileImg;
                    comment.message = etSendMessage.getText().toString();
                    comment.timestamp = ServerValue.TIMESTAMP;
                    //해당 방 찾아서 거기에 넣기 //채팅방 타이틀 이용하기
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomId).child("comments").push().setValue(comment).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            //푸시 알람 보내기
                            //입력 초기화
                            etSendMessage.setText("");
                        }
                    });
                    FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomId).child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            //채팅방에 존재하는 유저 받기
                            //users에 없으면 새로 만들어 넣기
                            for(DataSnapshot item : snapshot.getChildren()) {
                                if(item.getKey()!= userId){
                                    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
                                    DatabaseReference profileRef = firebaseDatabase.getReference().child("chatrooms").child(chatRoomId).child("users");
                                    //닉네임을 key 식별자로 하고 true
                                    profileRef.child(userId).setValue(true);
                                }else{
                                    break;
                                }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                        }
                    });

                }
            }
        });

        btnChatSTT.setOnClickListener(v -> {
            startSTT();
        });
    }

    void sendGcm(){
        Gson gson = new Gson();
        NotificationModel notificationModel = new NotificationModel();
        //push 받을 단말기의 토큰
        notificationModel.to = destinationUserModel.pushToken;
        //보낸사람
        notificationModel.notification.title = userId;
        notificationModel.notification.text = etSendMessage.getText().toString();

        notificationModel.data.title = userId;
        notificationModel.data.text = etSendMessage.getText().toString();

        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset= utf8"),gson.toJson(notificationModel));
        Request request = new Request.Builder()
                .header("Content-Type","application/json")
                .addHeader("Authorization","AAAAC_oWP0Y:APA91bHOMDVXlFO3DjqNeFz8fjoRFJv3n6ihZBj1IfNX-1NVlzL12qZddh3TCxS5rGJk2Kq-VuIddq-JOcOvbzTsM9KzYOm_VuUYXcFLM1eK2ibJ1Ih6BTLQ4jafJZAW3eXyJ1yn3E4O")
                .url("https://gcm-http.googleapis.com/gcm/send")
                .post(requestBody)
                .build();
        OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {

            }
        });

    }

    void checkChatRoom(){
        FirebaseDatabase.getInstance().getReference().child("chatrooms").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot item : snapshot.getChildren()){
                    //현재 존재하는 채팅방 정보들 받아오기
                    chatModel = item.getValue(ChatModel.class);
                    existChatTitles.add(item.getValue(ChatModel.class).ChatTitle);
                    Log.d("여기",chatModel.ChatTitle);
                    Boolean a =item.getValue(ChatModel.class).ChatTitle.equals(existChatRoomTitle);
                    Log.d("이유",a.toString());
                    if(chatModel.ChatTitle.equals(existChatRoomTitle)) {
                        //방 아이디 받아오면 버튼 살리기
                        chatRoomId = item.getKey();
                        Log.d("룸아이디", chatRoomId);
                        btnSend.setEnabled(true);
                        recyclerView.setHasFixedSize(true);
                        recyclerView.setLayoutManager(new LinearLayoutManager(ChatActivity.this));
                        recyclerView.setAdapter(new ChatAdater());
                        break;
                    }

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }




    class ChatAdater extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
        List<ChatModel.Comment> comments;
        public ChatAdater() {
            //comment 담기
            comments = new ArrayList<>();
            FirebaseDatabase.getInstance().getReference().child("chatrooms").orderByChild("ChatTitle/").equalTo(existChatRoomTitle).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    getMessageList();
                }


                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        void getMessageList(){
            FirebaseDatabase.getInstance().getReference().child("chatrooms").child(chatRoomId).child("comments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    comments.clear();
                    for(DataSnapshot item : snapshot.getChildren()){
                        comments.add(item.getValue(ChatModel.Comment.class));
                        Log.d("comments 출력", item.getValue(ChatModel.Comment.class).userId+item.getValue(ChatModel.Comment.class).message);
                    }
                    //데이터 갱신
                    notifyDataSetChanged();
                    //마지막으로 이동
                    recyclerView.scrollToPosition(comments.size()-1);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        }

        @NonNull
        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(
                    R.layout.item_message,parent,false
            );
            return new ChatViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
            ChatViewHolder chatViewHolder = (ChatViewHolder)holder;
            Log.d("유저",comments.get(position).userId.toString()+"왜");
            if(comments.get(position).userId.equals(userId)) {
                //나 일 경우
                Log.d("메세지",comments.get(position).message);
                chatViewHolder.message.setText(comments.get(position).message);
                chatViewHolder.layoutUserProfile.setVisibility(View.INVISIBLE);
                //오른쪽 정렬
                chatViewHolder.message.setBackgroundResource(R.drawable.my_box_background);
                chatViewHolder.layoutMessageMain.setGravity(Gravity.RIGHT);
            }else{
                //상대방 일 경우
                Glide.with(holder.itemView.getContext())
                        .load(comments.get(position).userProfileImg)
                        .into(chatViewHolder.userProfile);
                chatViewHolder.userId.setText(comments.get(position).userId);
                chatViewHolder.message.setText(comments.get(position).message);
                chatViewHolder.layoutUserProfile.setVisibility(View.VISIBLE);
                //상대 말풍선 background
                chatViewHolder.message.setBackgroundResource(R.drawable.other_box_background);
                chatViewHolder.layoutMessageMain.setGravity(Gravity.LEFT);
            }
            long unixTime = (long) comments.get(position).timestamp;
            Date date = new Date(unixTime);
            simpleDateFormat.setTimeZone(TimeZone.getTimeZone("Asia/Seoul"));
            String time = simpleDateFormat.format(date);
            chatViewHolder.timeStamp.setText(time);
        }

        @Override
        public int getItemCount() {
            return comments.size();
        }

        private class ChatViewHolder extends RecyclerView.ViewHolder {
            public TextView message;
            public TextView userId;
            public CircleImageView userProfile;
            public LinearLayout layoutUserProfile;
            public LinearLayout layoutMessageMain;
            public TextView timeStamp;

            public ChatViewHolder(View view) {
                super(view);
                message = (TextView) view.findViewById(R.id.tv_message);
                userId = (TextView) view.findViewById(R.id.tv_user_name);
                timeStamp = (TextView) view.findViewById(R.id.tv_chat_time);
                userProfile = (CircleImageView) view.findViewById(R.id.cv_profile);
                layoutUserProfile = (LinearLayout)view.findViewById(R.id.ly_message_item_dest);
                layoutMessageMain = (LinearLayout)view.findViewById(R.id.ly_message_main);

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:{ //toolbar의 back키 눌렀을 때 동작
                finish();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    void startSTT(){
        if ( Build.VERSION.SDK_INT >= 23 ){ // 퍼미션 체크
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET, Manifest.permission.RECORD_AUDIO},PERMISSION);
        }

        intent=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE,getPackageName());
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE,"ko-KR");

        mRecognizer=SpeechRecognizer.createSpeechRecognizer(this);
        mRecognizer.setRecognitionListener(listener);
        mRecognizer.startListening(intent);
    }

    private RecognitionListener listener = new RecognitionListener() {
        @Override public void onReadyForSpeech(Bundle params) {
            Toast.makeText(getApplicationContext(),"음성인식을 시작합니다.",Toast.LENGTH_SHORT).show();
        }
        @Override public void onBeginningOfSpeech() {}

        @Override public void onRmsChanged(float rmsdB) {}

        @Override public void onBufferReceived(byte[] buffer) {}

        @Override public void onEndOfSpeech() {}

        @Override public void onError(int error) { String message;
            switch (error) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "오디오 에러";
                    break;
                case SpeechRecognizer.ERROR_CLIENT: message = "클라이언트 에러"; break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS: message = "퍼미션 없음"; break;
                case SpeechRecognizer.ERROR_NETWORK: message = "네트워크 에러"; break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT: message = "네트웍 타임아웃"; break;
                case SpeechRecognizer.ERROR_NO_MATCH: message = "찾을 수 없음"; break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY: message = "RECOGNIZER가 바쁨"; break;
                case SpeechRecognizer.ERROR_SERVER: message = "서버가 에러"; break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT: message = "말하는 시간초과"; break;
                default: message = "알 수 없는 오류"; break;
            }

            Toast.makeText(getApplicationContext(), message,Toast.LENGTH_SHORT).show();

        }

        @Override public void onResults(Bundle results) {
            // 말을 하면 ArrayList에 단어를 넣고 textView에 단어를 이어줍니다.
            ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            for(int i = 0; i < matches.size() ; i++){
                etSendMessage.setText(matches.get(i));
            }
        }

        @Override public void onPartialResults(Bundle partialResults) {}

        @Override public void onEvent(int eventType, Bundle params) {}
    };
}
