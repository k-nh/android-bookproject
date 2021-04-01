package com.example.bookreviewsver112.src.report

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver112.R
import com.example.bookreviewsver112.config.BaseFragment
import com.example.bookreviewsver112.databinding.FragmentReportListBinding

class ReportListFragment : BaseFragment<FragmentReportListBinding>(FragmentReportListBinding::bind, R.layout.fragment_report_list) {
    private var mArrayList: ArrayList<ReportItem>? = null
    private var mAdapter: ReportAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mRecyclerView = binding.rvPostList as RecyclerView
        val mLinearLayoutManager = LinearLayoutManager(context)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mArrayList = ArrayList()
        mAdapter = ReportAdapter(mArrayList)
        mRecyclerView.adapter = mAdapter

        //***********
        //데이터베이스에서 데이터 불러오기
        val titleData: String = ""
        //제목 데이터 저장
        val contentsData: String = ""
        //내용 데이터 저장
        val item = ReportItem(titleData,contentsData)
        val count = mArrayList!!.size
        for(i in 0..count){
            mArrayList!!.add(0, item)
        }

        //구분선
        val dividerItemDecoration = DividerItemDecoration(mRecyclerView.context,
                mLinearLayoutManager.orientation)
        mRecyclerView.addItemDecoration(dividerItemDecoration)

        //list 클릭
        mRecyclerView.addOnItemTouchListener(RecyclerTouchListener(activity, mRecyclerView, object : ClickListener {
            override fun onClick(view: View?, position: Int) {
                val item = mArrayList!![position]
                val intent = Intent(activity, ReportPostActivity::class.java)
                intent.putExtra("titleData", item.titleText)
                intent.putExtra("contentsData", item.contentsText)
                startActivity(intent)
            }

            override fun onLongClick(view: View?, position: Int) {

            }
        }))

        //button 클릭
        binding.btPostAdd.setOnClickListener{
            //ReportWriterActivity 로 전환
            val intent = Intent(activity,ReportWriterActivity::class.java)
            startActivity(intent)
        }
    }

}

//recyclerview 터치 관련
interface ClickListener {
    fun onClick(view: View?, position: Int)
    fun onLongClick(view: View?, position: Int)
}

class RecyclerTouchListener(context: Context?, recyclerView: RecyclerView, private val clickListener: ClickListener?) :
        RecyclerView.OnItemTouchListener {
    private val gestureDetector: GestureDetector
    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val child = rv.findChildViewUnder(e.x, e.y)
        if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
            clickListener.onClick(child, rv.getChildAdapterPosition(child))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {}
    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {}

    init {
        gestureDetector = GestureDetector(context, object : GestureDetector.SimpleOnGestureListener() {
            override fun onSingleTapUp(e: MotionEvent): Boolean {
                return true
            }

            override fun onLongPress(e: MotionEvent) {
                val child = recyclerView.findChildViewUnder(e.x, e.y)
                if (child != null && clickListener != null) {
                    clickListener.onLongClick(child, recyclerView.getChildAdapterPosition(child))
                }
            }
        })
    }
}

