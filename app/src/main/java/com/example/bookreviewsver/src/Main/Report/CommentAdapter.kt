package com.example.bookreviewsver.src.Main.Report

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.GetCommentResult
import com.example.bookreviewsver.src.home.OtherPageActivity
import de.hdodenhof.circleimageview.CircleImageView

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


class CommentViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var userId: String? = MyApplication.sSharedPreferences.getString("userId", null)
    private val commentContent = itemView.findViewById<TextView>(R.id.tv_comment_content)
    private val commentUserProfile = itemView.findViewById<CircleImageView>(R.id.cv_comment_profileImg)
    private val commentId = itemView.findViewById<TextView>(R.id.tv_comment_id)
    //private val commentEditContent = itemView.findViewById<TextView>(R.id.et_comment_content)
    //private val commentEdit = itemView.findViewById<TextView>(R.id.edit_comment)
    //private val commentDelete = itemView.findViewById<TextView>(R.id.delete_comment)
   // private val commentEditComplete = itemView.findViewById<TextView>(R.id.edit_comment_complete)
    private val context = itemView.context

    //뷰와 데이터를 bind 묶음
    //result 변경해야함
    fun bindWithView(CommentItem: GetCommentResult) {
        Log.d(Constants.TAG, "ReportViewHolder- bindWithView() called")

        commentId.text = CommentItem.id
        commentContent.text = CommentItem.comment
        Glide.with(MyApplication.instance)
            .load(CommentItem.profileImgUrl)
            .placeholder(R.drawable.default_profile)
            .into(commentUserProfile)

        commentId.setOnClickListener {
            Intent(context, OtherPageActivity::class.java).apply {
                putExtra("userId", CommentItem.id)
            }.run { context.startActivity(this)
            }
        }

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
}