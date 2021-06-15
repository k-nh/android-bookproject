package com.example.bookreviewsver.src.Main.Home

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Book.BookDetailActivity
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.model.BestSellerData

class BestSellerAdapter: RecyclerView.Adapter<BestSellerViewHolder>(){
    private var BestSellerList = ArrayList<BestSellerData>()

    //뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val bestSellerViewHolder = BestSellerViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_best_seller,parent,false)
        )
        return bestSellerViewHolder
    }

    //뷰가 bind 됐을때 데이터를 뷰 홀더에 넘겨준다
    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        holder.bindWithView(this.BestSellerList[position])

    }

    //보여줄 목록 개수
    override fun getItemCount(): Int {
        return this.BestSellerList.size
    }

    //외부에서 어댑터에 데이터 배열을 넣어준다
    //class BestSellerAdapter(BestSellerList: ArrayList<BestSellerData>) : RecyclerView.Adapter<BestSellerViewHolder>(){
    //이런식으로 클래스 명에 넣어줄 수도 있음
    fun submitList(BestSellerList: ArrayList<BestSellerData>){
        this.BestSellerList = BestSellerList
    }


}

class BestSellerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    //뷰들 가져옴
    private val bookCardView = itemView.findViewById<CardView>(R.id.ly_best_seller_item)
    private val bookRank = itemView.findViewById<TextView>(R.id.tv_book_rank)
    private val bookImgView = itemView.findViewById<ImageView>(R.id.iv_book_img)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_book_title)
    private val bookAuthor = itemView.findViewById<TextView>(R.id.tv_book_author)
    private val bookPublisher = itemView.findViewById<TextView>(R.id.tv_book_publisher)

    //private val bookDescription = itemView.findViewById<TextView>(R.id.tv_description)
    //private val bookLikeFalse = itemView.findViewById<ImageView>(R.id.iv_favorite_false)
    //private val addBook = itemView.findViewById<ImageView>(R.id.iv_add_book)

    private val context = itemView.context
    val inflater: LayoutInflater = LayoutInflater.from(context)

    //뷰와 데이터를 bind 묶음
    fun bindWithView(BestSellerItem: BestSellerData) {
        Log.d(Constants.TAG, "ViewHolder- bindWithView() called")

        bookCardView.setOnClickListener {
            Log.d(Constants.TAG, "bestsellerViewHolder- click() called")
            Intent(context, BookDetailActivity::class.java).apply {
                MyApplication.sSharedPreferences.edit().putString("isbn", BestSellerItem.isbn!!).apply()
                MyApplication.sSharedPreferences.edit().putString("title", BestSellerItem.title!!).apply()
                MyApplication.sSharedPreferences.edit().putString("bookImgUrl", BestSellerItem.coverImgUrl!!).apply()
                MyApplication.sSharedPreferences.edit().putInt("bookId", BestSellerItem.itemId!!).apply()
            }.run { context.startActivity(this) }
        }

        //best seller 이미지 설정 by url
        //context 대신 MyApplication.instance 로 전역으로 가져옴
        Glide.with(MyApplication.instance)
            .load(BestSellerItem.coverImgUrl)
            .placeholder(R.drawable.ic_baseline_photo_24)
            .into(bookImgView)

        // 나머지 정보들 넣기
        bookRank.text = BestSellerItem.rank.toString()
        bookTitle.text = BestSellerItem.title
        bookAuthor.text = BestSellerItem.author
        bookPublisher.text = BestSellerItem.publisher
        //bookDescription.text = BestSellerItem.description

    }



}