package com.example.bookreviewsver.src.Main.Report.`interface`

//import com.example.bookreviewsver.src.Main.report.model.PostReportResponse
import com.example.bookreviewsver.src.Main.Report.model.*

interface ReportActivityView {
    fun reportListSuccess(reportResponse: ReportResponse)
    fun reportListFailure(message: String)

    fun postReportSuccess(postReportResponse: PostReportResponse)
    fun postReportFailure(message: String)

    fun getReportSuccess(getReportResponse: GetReportResponse)
    fun getReportFailure(message: String)

    fun editReportSuccess(editReportResponse: EditReportResponse)
    fun editCommentSuccess(editCommentResponse: EditCommentResponse)
    fun postCommentSuccss(postCommentResponse: PostCommentResponse)
    fun deleteReportSuccss()
    fun getCommentSuccss(getCommentResponse: GetCommentResponse)
    fun getLikeFeedListSuccss(likeFeedListResponse: LikeFeedListResponse)
    fun getLikeFeedIdListSuccss(likeFeedIdListResponse: LikeFeedIdListResponse)





}