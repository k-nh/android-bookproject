package com.example.bookreviewsver.src.Main.Report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Report.model.GetCommentResult

class CommentAdapter: RecyclerView.Adapter<CommentViewHolder>() {

    //result 변경해야함
    private var commentArrayList = ArrayList<GetCommentResult>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommentViewHolder {
        val commentViewHolder = CommentViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_report_comment, parent, false
            )
        )
        return commentViewHolder
    }

    override fun onBindViewHolder(holder: CommentViewHolder, position: Int) {
        holder.bindWithView(this.commentArrayList[position])

    }

    override fun getItemCount(): Int {
        return this.commentArrayList.size
    }

    fun submitList(CommentList: List<GetCommentResult>) {
        this.commentArrayList = CommentList as ArrayList<GetCommentResult>
    }
}