package com.example.bookreviewsver.src.Main.Report

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants.TAG
import com.example.bookreviewsver.src.Main.Report.model.Result
import com.example.bookreviewsver.src.report.ReportPostActivity

class ReportViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    private val reportItem = itemView.findViewById<LinearLayout>(R.id.ly_report_list_item)
    private val reportTitle = itemView.findViewById<TextView>(R.id.tv_report_item_title)
    private val reportContent = itemView.findViewById<TextView>(R.id.tv_report_item_contents)
    private val writerID = itemView.findViewById<TextView>(R.id.tv_report_item_name)
    private val reportRegDate = itemView.findViewById<TextView>(R.id.tv_report_item_regdate)
    private val bookCoverImg = itemView.findViewById<ImageView>(R.id.img_report_item_book)
    private val context = itemView.context

    //뷰와 데이터를 bind 묶음
    fun bindWithView(ReportItem: Result) {
        Log.d(TAG, "ReportViewHolder- bindWithView() called")
        reportItem.setOnClickListener {
            Intent(context, ReportPostActivity::class.java).apply {
                putExtra("reportId",ReportItem.repostId)
            }.run { context.startActivity(this) }
        }

        writerID.text = ReportItem.id
        reportRegDate.text = ReportItem.regdate
        reportTitle.text = ReportItem.title
        reportContent.text = ReportItem.content

        Glide.with(MyApplication.instance)
                .load(ReportItem.coverImgUrl)
                .placeholder(R.drawable.ic_baseline_photo_24)
                .into(bookCoverImg)

    }
}
