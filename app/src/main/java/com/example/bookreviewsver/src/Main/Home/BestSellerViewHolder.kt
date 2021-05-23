package com.example.bookreviewsver.src.Main.Home

import android.content.Intent
import android.util.Log
import android.view.View
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants.TAG
import com.example.bookreviewsver.src.Main.Home.`interface`.HomeFragmentView
import com.example.bookreviewsver.src.Main.Home.model.BestSellerData
import com.example.bookreviewsver.src.Main.Home.model.LikeResponse
import com.example.bookreviewsver.src.Main.Home.service.HomeRetrofitManager
import com.example.bookreviewsver.src.Main.MyPage.model.LikeListResponse

class BestSellerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), HomeFragmentView {
    //뷰들 가져옴
    private val bookCardView = itemView.findViewById<CardView>(R.id.ly_best_seller_item)
    private val bookRank = itemView.findViewById<TextView>(R.id.tv_book_rank)
    private val bookImgView = itemView.findViewById<ImageView>(R.id.iv_book_img)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_book_title)
    private val bookAuthor = itemView.findViewById<TextView>(R.id.tv_book_author)
    private val bookPublisher = itemView.findViewById<TextView>(R.id.tv_book_publisher)
    //private val bookDescription = itemView.findViewById<TextView>(R.id.tv_description)
    private val bookLikeFalse = itemView.findViewById<ImageView>(R.id.iv_favorite_false)
    private val bookLikeTrue = itemView.findViewById<ImageView>(R.id.iv_favorite_true)
    private val context = itemView.context


    //뷰와 데이터를 bind 묶음
    fun bindWithView(BestSellerItem: BestSellerData){
        Log.d(TAG,"ViewHolder- bindWithView() called")

        bookCardView.setOnClickListener{
            Log.d(TAG,"bestsellerViewHolder- click() called")
            Intent(context, BookDetailActivity::class.java).apply {
                putExtra("bookId",BestSellerItem.itemId)
                putExtra("title",BestSellerItem.title)
                putExtra("author",BestSellerItem.author)
                putExtra("publisher",BestSellerItem.publisher)
                putExtra("description",BestSellerItem.description)
                putExtra("coverImgUrl",BestSellerItem.coverImgUrl)
                putExtra("pubDate",BestSellerItem.pubDate)
                putExtra("link",BestSellerItem.link)
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

        bookLikeFalse.setOnClickListener(View.OnClickListener {
            //bookid, bookname, bookimgurl, categoryid
            HomeRetrofitManager.instance.postBookLike(BestSellerItem.itemId!!,BestSellerItem.title!!, BestSellerItem.coverImgUrl!!,BestSellerItem.categoryId!!)
            BestSellerItem.favorite = true
            bookLikeFalse.visibility = INVISIBLE
            bookLikeTrue.visibility = VISIBLE
        })

        bookLikeTrue.setOnClickListener(View.OnClickListener {
            HomeRetrofitManager.instance.postBookLike(BestSellerItem.itemId!!,BestSellerItem.title!!, BestSellerItem.coverImgUrl!!,BestSellerItem.categoryId!!)
            BestSellerItem.favorite = false
            bookLikeTrue.visibility = INVISIBLE
            bookLikeFalse.visibility = VISIBLE
        })


        if (BestSellerItem.favorite == true) {
            bookLikeFalse.visibility = INVISIBLE
            bookLikeTrue.visibility = VISIBLE
        }else{
            bookLikeFalse.visibility = VISIBLE
            bookLikeTrue.visibility = INVISIBLE
        }


    }

    override fun postBookLikeSuccess(bookLikeResponse: LikeResponse) {
        Log.d("postBookLikeSuccess","좋아요 성공")

    }

    override fun postBookLikeFailure(message: String?) {
    }

    override fun getLikeListSuccess(LikeListResponse: LikeListResponse) {
        TODO("Not yet implemented")
    }

    override fun getLikeListFailure(message: String?) {
        TODO("Not yet implemented")
    }


}