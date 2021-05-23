package com.example.bookreviewsver.src.Main.Report

import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*

class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), ReportActivityView {
    var userId: String? = MyApplication.sSharedPreferences.getString("userId",null)
    private val commentContent = itemView.findViewById<TextView>(R.id.tv_comment_content)
    private val commentEditContent = itemView.findViewById<TextView>(R.id.et_comment_content)
    private val commentId = itemView.findViewById<TextView>(R.id.tv_comment_id)
    private val commentEdit = itemView.findViewById<TextView>(R.id.edit_comment)
    private val commentDelete = itemView.findViewById<TextView>(R.id.delete_comment)
    private val commentEditComplete = itemView.findViewById<TextView>(R.id.edit_comment_complete)
    private val context = itemView.context

    //뷰와 데이터를 bind 묶음
    //result 변경해야함
    fun bindWithView(CommentItem: GetCommentResult) {
        Log.d(Constants.TAG, "ReportViewHolder- bindWithView() called")

        commentId.text = CommentItem.id
        commentContent.text = CommentItem.comment

    //나의 아이디와 postCommentResponse의 아이디가 일치하면 수정 삭제 버튼 보이게
//        if(CommentItem.id == userId) {
//            commentEdit.visibility = View.VISIBLE
//            commentDelete.visibility = View.VISIBLE
//        }
//
//        //수정 버튼 누르면
//        commentEdit.setOnClickListener{
//            //visible, invisible
//            commentEdit.visibility = View.INVISIBLE
//            commentDelete.visibility = View.INVISIBLE
//            commentContent.visibility = View.INVISIBLE
//            commentEditContent.visibility = View.VISIBLE
//            commentEditComplete.visibility = View.VISIBLE
//        }
//
//        //완료버튼 누르면
//        commentEditComplete.setOnClickListener{
//            //service 보내기
//            ReportService(this).editComment(commentEditContent,)
//
//
//        }
//
//        //삭제버튼 누르면
//        commentDelete.setOnClickListener{
//            //service 보내기
//
//        }


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
    }

    override fun getCommentSuccss(getCommentResponse: GetCommentResponse) {
        TODO("Not yet implemented")
    }
}
