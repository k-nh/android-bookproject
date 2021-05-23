package com.example.bookreviewsver.src.Main.Report.service

import android.util.Log
import com.example.application.config.MyApplication
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants.TAG
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API
import com.example.bookreviewsver.src.Main.SignIn.`interface`.API.BASE_URL
import com.example.bookreviewsver.src.Main.Report.`interface`.ReportActivityView
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.home.`interface`.ReportInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ReportService(val view: ReportActivityView) {

    fun getBoardList() {
        val reportInterface: ReportInterface? = MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(ReportInterface::class.java)
        reportInterface?.getBoardList()?.enqueue(object : retrofit2.Callback<ReportResponse?> {
            override fun onResponse(call: Call<ReportResponse?>, response: Response<ReportResponse?>) {
                Log.d(TAG,"getBoardList 성공")
                view.reportListSuccess(response.body() as ReportResponse)
            }

            override fun onFailure(call: Call<ReportResponse?>, t: Throwable) {
                view.reportListFailure(t.message ?: "통신오류")
            }
        })
    }

    fun getReport(reportId: Int) {
        val reportInterface: ReportInterface? = MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(ReportInterface::class.java)
        reportInterface?.getReport(reportId)?.enqueue(object : retrofit2.Callback<GetReportResponse?> {
            override fun onResponse(call: Call<GetReportResponse?>, response: Response<GetReportResponse?>) {
                Log.d(TAG,"getBoardList 성공")
                view.getReportSuccess(response.body() as GetReportResponse)
            }

            override fun onFailure(call: Call<GetReportResponse?>, t: Throwable) {
                view.getReportFailure(t.message ?: "통신오류")
            }
        })
    }

    fun editReport(title: String, content : String, reportId:Int) {val reportInterface: ReportInterface? =
        MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(ReportInterface::class.java)
        reportInterface?.editReport(EditReportBody(title,content),reportId)?.enqueue(object : Callback<EditReportResponse?> {
            override fun onResponse(
                call: Call<EditReportResponse?>,
                response: Response<EditReportResponse?>
            ) {
                Log.d("editReport", "성공")
                view.editReportSuccess(response.body() as EditReportResponse)

            }

            override fun onFailure(call: Call<EditReportResponse?>, t: Throwable) {
                Log.d("editReport", "실패")
            }
        })
    }


    fun editComment(content : String, commentId:Int) {
    val reportInterface: ReportInterface? =
        MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(ReportInterface::class.java)
        reportInterface?.editComment(EditCommentBody(content),commentId)?.enqueue(object : Callback<EditCommentResponse?> {
            override fun onResponse(
                call: Call<EditCommentResponse?>,
                response: Response<EditCommentResponse?>
            ) {
                Log.d("editReport", "성공")
                view.editCommentSuccess(response.body() as EditCommentResponse)

            }

            override fun onFailure(call: Call<EditCommentResponse?>, t: Throwable) {
                Log.d("editComment", "실패")
            }
        })
    }

    fun postReport(reportTitle:String, bookId: Int, bookName:String, reportContent:String, userId:String, coverImgUrl:String) {
        val reportInterface: ReportInterface? =
                MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(ReportInterface::class.java)
        reportInterface?.postReport(PostReportBody(reportTitle,bookId,bookName,reportContent,userId,coverImgUrl))?.enqueue(object : Callback<PostReportResponse?> {
            override fun onResponse(
                    call: Call<PostReportResponse?>,
                    response: Response<PostReportResponse?>
            ) {
                Log.d("postBookLike", "성공")
                view.postReportSuccess(response.body() as PostReportResponse)
            }

            override fun onFailure(call: Call<PostReportResponse?>, t: Throwable) {
                view.postReportFailure(t.message ?: "통신오류")
            }
        })
    }

    fun postComment(reportId: Int, comment:String, Id:String) {
        val reportInterface: ReportInterface? =
            MyApplication.RetrofitClient.getCleint(API.BASE_URL)?.create(ReportInterface::class.java)
        reportInterface?.postComment(CommentBody(reportId,comment,Id))?.enqueue(object : Callback<PostCommentResponse?> {
            override fun onResponse(
                call: Call<PostCommentResponse?>,
                response: Response<PostCommentResponse?>
            ) {
                Log.d("postBookLike", "성공")
                view.postCommentSuccss(response.body() as PostCommentResponse)
            }

            override fun onFailure(call: Call<PostCommentResponse?>, t: Throwable) {
            }
        })
    }


    fun getCommentList(reportId: Int) {
        val reportInterface: ReportInterface? = MyApplication.RetrofitClient.getCleint(BASE_URL)?.create(ReportInterface::class.java)
        reportInterface?.getCommentList(reportId)?.enqueue(object : retrofit2.Callback<GetCommentResponse?> {
            override fun onResponse(call: Call<GetCommentResponse?>, response: Response<GetCommentResponse?>) {
                Log.d(TAG,"getBoardList 성공")
                view.getCommentSuccss(response.body() as GetCommentResponse)
            }

            override fun onFailure(call: Call<GetCommentResponse?>, t: Throwable) {
            }
        })
    }

}