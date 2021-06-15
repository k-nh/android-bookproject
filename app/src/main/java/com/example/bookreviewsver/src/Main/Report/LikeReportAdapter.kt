package com.example.bookreviewsver.src.report

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants.TAG
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.Main.Report.service.ReportService


class LikeReportAdapter: RecyclerView.Adapter<LikeReportViewHolder>() {

    private var ReportArrayList = ArrayList<LikeFeedResult>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LikeReportViewHolder {
        val likeReportViewHolder = LikeReportViewHolder(LayoutInflater.from(parent.context).inflate(
            R.layout.item_report_list, parent, false))
        return likeReportViewHolder
    }

    override fun onBindViewHolder(holder: LikeReportViewHolder, position: Int) {
        holder.bindWithView(this.ReportArrayList[position])

    }

    override fun getItemCount(): Int {
        return this.ReportArrayList.size
    }

    fun submitList(ReportList: List<LikeFeedResult>){
        this.ReportArrayList = ReportList as ArrayList<LikeFeedResult>
    }
}

class LikeReportViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), ReportActivityView {
    private val reportItem = itemView.findViewById<LinearLayout>(R.id.ly1)
    private val reportTitle = itemView.findViewById<TextView>(R.id.tv_report_item_title)
    private val reportContent = itemView.findViewById<TextView>(R.id.tv_report_item_contents)
    private val writerID = itemView.findViewById<TextView>(R.id.tv_report_item_name)
    private val reportRegDate = itemView.findViewById<TextView>(R.id.tv_report_item_regdate)
    private val bookTitle = itemView.findViewById<TextView>(R.id.tv_report_item_book_title)
    private val bookCoverImg = itemView.findViewById<ImageView>(R.id.img_report_item_book)
    private val profileImg = itemView.findViewById<ImageView>(R.id.iv_report_item_profile)
    private val likeFeed = itemView.findViewById<ImageView>(R.id.feed_like)
    private val likeFeedClicked = itemView.findViewById<ImageView>(R.id.feed_like_click)
    private val context = itemView.context

    //뷰와 데이터를 bind 묶음
    fun bindWithView(ReportItem: LikeFeedResult) {
        Log.d(TAG, "좋아요- bindWithView() called")
        reportItem.setOnClickListener {
            Intent(context, ReportPostActivity::class.java).apply {
                putExtra("reportId", ReportItem.repostId)
            }.run { context.startActivity(this) }
        }
        likeFeed.visibility = View.INVISIBLE
        likeFeedClicked.visibility = View.VISIBLE
        writerID.text = ReportItem.writer
        reportRegDate.text = ReportItem.regdate
        reportTitle.text = ReportItem.title
        reportContent.text = ReportItem.content
        bookTitle.text = ReportItem.bookName

        Glide.with(MyApplication.instance)
            .load(ReportItem.coverImgUrl)
            .placeholder(R.drawable.ic_baseline_photo_24)
            .into(bookCoverImg)

        Glide.with(MyApplication.instance)
            .load(ReportItem.profileImgUrl)
            .placeholder(R.drawable.default_profile)
            .into(profileImg)


        likeFeed.setOnClickListener {
            likeFeed.visibility = View.INVISIBLE
            likeFeedClicked.visibility = View.VISIBLE
            ReportService(this).postFeedLike(ReportItem.repostId,ReportItem.title,ReportItem.bookID,ReportItem.bookName,ReportItem.content!!,ReportItem.writer,ReportItem.coverImgUrl,ReportItem.profileImgUrl,ReportItem.regdate!!)
        }

        likeFeedClicked.setOnClickListener {
            likeFeedClicked.visibility = View.INVISIBLE
            likeFeed.visibility = View.VISIBLE
            ReportService(this).postFeedLike(ReportItem.repostId,ReportItem.title,ReportItem.bookID,ReportItem.bookName,ReportItem.content!!,ReportItem.writer,ReportItem.coverImgUrl,ReportItem.profileImgUrl,ReportItem.regdate!!)
        }
    }

    override fun reportListSuccess(reportResponse: ReportResponse) {
        TODO("Not yet implemented")
    }

    override fun reportListFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun postReportSuccess(postReportResponse: PostReportResponse) {
        TODO("Not yet implemented")
    }

    override fun postReportFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun getReportSuccess(getReportResponse: GetReportResponse) {
        TODO("Not yet implemented")
    }

    override fun getReportFailure(message: String) {
        TODO("Not yet implemented")
    }

    override fun editReportSuccess(editReportResponse: EditReportResponse) {
        TODO("Not yet implemented")
    }

    override fun editCommentSuccess(editCommentResponse: EditCommentResponse) {
        TODO("Not yet implemented")
    }

    override fun postCommentSuccss(postCommentResponse: PostCommentResponse) {
        TODO("Not yet implemented")
    }

    override fun deleteReportSuccss() {
        TODO("Not yet implemented")
    }

    override fun getCommentSuccss(getCommentResponse: GetCommentResponse) {
        TODO("Not yet implemented")
    }

    override fun getLikeFeedListSuccss(likeFeedListResponse: LikeFeedListResponse) {
    }

    override fun getLikeFeedIdListSuccss(likeFeedIdListResponse: LikeFeedIdListResponse) {
        TODO("Not yet implemented")
    }
}



