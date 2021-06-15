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

class RecommendAdapter: RecyclerView.Adapter<RecommendViewHolder>(){
    private var RecommendList = ArrayList<BestSellerData>()

    //뷰홀더와 레이아웃 연결
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecommendViewHolder {
        val bestSellerViewHolder = RecommendViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_recommend_book,parent,false)
        )
        return bestSellerViewHolder
    }

    //뷰가 bind 됐을때 데이터를 뷰 홀더에 넘겨준다
    override fun onBindViewHolder(holder: RecommendViewHolder, position: Int) {
        holder.bindWithView(this.RecommendList[position])

    }

    //보여줄 목록 개수
    override fun getItemCount(): Int {
        return this.RecommendList.size
    }

    //외부에서 어댑터에 데이터 배열을 넣어준다
    //class BestSellerAdapter(BestSellerList: ArrayList<BestSellerData>) : RecyclerView.Adapter<BestSellerViewHolder>(){
    //이런식으로 클래스 명에 넣어줄 수도 있음
    fun submitList(RecommendList: ArrayList<BestSellerData>){
        this.RecommendList = RecommendList
    }


}

class RecommendViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    //뷰들 가져옴
    private val bookCardView = itemView.findViewById<CardView>(R.id.cardview_favorite_item)
    private val bookImgView = itemView.findViewById<ImageView>(R.id.iv_favorite_book_img)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_favorite_book_title)
    //private val bookAuthor = itemView.findViewById<TextView>(R.id.tv_book_author)
    //private val bookPublisher = itemView.findViewById<TextView>(R.id.tv_book_publisher)
    //private val bookDescription = itemView.findViewById<TextView>(R.id.tv_description)
    private val context = itemView.context


    //뷰와 데이터를 bind 묶음
    fun bindWithView(BestSellerItem: BestSellerData){
        Log.d(Constants.TAG,"ViewHolder- bindWithView() called")

        bookCardView.setOnClickListener{
            Log.d(Constants.TAG,"bestsellerViewHolder- click() called")
            Intent(context, BookDetailActivity::class.java).apply {
                MyApplication.sSharedPreferences.edit().putString("isbn", BestSellerItem.isbn).apply()
            }.run { context.startActivity(this) }
        }

        //best seller 이미지 설정 by url
        //context 대신 MyApplication.instance 로 전역으로 가져옴
        Glide.with(MyApplication.instance)
                .load(BestSellerItem.coverImgUrl)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(bookImgView)

        // 나머지 정보들 넣기
        //bookRank.text = BestSellerItem.rank.toString()
        bookTitle.text = BestSellerItem.title
        //bookAuthor.text = BestSellerItem.author
        //bookPublisher.text = BestSellerItem.publisher
        //bookDescription.text = BestSellerItem.description

//        bookLikeFalse.setOnClickListener(View.OnClickListener {
//            //bookid, bookname, bookimgurl, categoryid
//            HomeRetrofitManager.instance.postBookLike(BestSellerItem.itemId!!,BestSellerItem.title!!, BestSellerItem.coverImgUrl!!,BestSellerItem.categoryId!!)
//            BestSellerItem.favorite = true
//            bookLikeFalse.visibility = View.INVISIBLE
//            bookLikeTrue.visibility = View.VISIBLE
//        })
//
//        bookLikeTrue.setOnClickListener(View.OnClickListener {
//            HomeRetrofitManager.instance.postBookLike(BestSellerItem.itemId!!,BestSellerItem.title!!, BestSellerItem.coverImgUrl!!,BestSellerItem.categoryId!!)
//            BestSellerItem.favorite = false
//            bookLikeTrue.visibility = View.INVISIBLE
//            bookLikeFalse.visibility = View.VISIBLE
//        })
//
//
//        if (BestSellerItem.favorite == true) {
//            bookLikeFalse.visibility = View.INVISIBLE
//            bookLikeTrue.visibility = View.VISIBLE
//        }else{
//            bookLikeFalse.visibility = View.VISIBLE
//            bookLikeTrue.visibility = View.INVISIBLE
//        }


    }

}
