package com.example.bookreviewsver.src.Main.Chat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ChatModel {
    public int bookId;
    public String bookImgUrl;
    public String ChatTitle;

    //채팅방 유저들
    public Map<String, Boolean> users = new HashMap<>();
    //채팅방 대화 내용
    public Map<String, Comment> comments = new HashMap<>();

    public static class Comment{
        public String userId;
        public String userProfileImg;
        public String message;
        public Object timestamp;
    }

}
