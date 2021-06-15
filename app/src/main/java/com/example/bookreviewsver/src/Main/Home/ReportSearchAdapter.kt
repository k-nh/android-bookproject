package com.example.bookreviewsver.src.Main.Home.service

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.MainActivity
import com.example.bookreviewsver.R
import com.example.bookreviewsver.databinding.ActivityReportWriteBinding
import com.example.bookreviewsver.src.Main.Book.BookDetailActivity
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.model.SearchData
import com.example.bookreviewsver.src.Main.Home.service.HomeRetrofitManager
import com.example.bookreviewsver.src.report.ReportWriteActivity

class ReportSearchAdapter(): RecyclerView.Adapter<SearchViewHolder>() {

    private var SearchBookList = ArrayList<SearchData>()


    interface ItemClickListener {
        fun onClick(SearchItem: SearchData)
    }

    //클릭리스너 선언
    private lateinit var itemClickListner: ItemClickListener

    //클릭리스너 등록 매소드
    fun setItemClickListener(itemClickListener: ItemClickListener) {
        this.itemClickListner = itemClickListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val searchViewHolder = SearchViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_search_book,parent,false))
        return searchViewHolder    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bindWithView(this.SearchBookList[position])

        holder.itemView.setOnClickListener {
            itemClickListner.onClick(this.SearchBookList[position])
        }

    }

    override fun getItemCount(): Int {
        return this.SearchBookList.size
    }

    fun submitList(SearchBookList: ArrayList<SearchData>){
        this.SearchBookList = SearchBookList
    }
}

class SearchViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val searchCardView = itemView.findViewById<CardView>(R.id.cardview_search_book_item)
    private val bookImgView = itemView.findViewById<ImageView>(R.id.iv_search_book_img)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_search_book_title)
    private val bookAuthor = itemView.findViewById<TextView>(R.id.tv_search_book_author)
    private val bookPublisher = itemView.findViewById<TextView>(R.id.tv_search_book_publisher)
    private val bookPubDate = itemView.findViewById<TextView>(R.id.tv_search_book_pubdate)
    private val context = itemView.context


    //뷰와 데이터를 bind 묶음
    fun bindWithView(SearchItem: SearchData) {
        Log.d(Constants.TAG, "SearchViewHolder- bindWithView() called")

        //best seller 이미지 설정 by url
        //context 대신 MyApplication.instance 로 전역으로 가져옴
        Glide.with(MyApplication.instance)
                .load(SearchItem.coverImgUrl)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(bookImgView)

        // 나머지 정보들 넣기
        bookTitle.text = SearchItem.title
        bookAuthor.text = SearchItem.author
        bookPublisher.text = SearchItem.publisher
        //    bookDescription.text = SearchItem.description
        bookPubDate.text = SearchItem.pubDate

    }
}
