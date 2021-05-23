package com.example.bookreviewsver.src.report

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Report.ReportViewHolder
import com.example.bookreviewsver.src.Main.Report.model.Result


class ReportAdapter: RecyclerView.Adapter<ReportViewHolder>() {

    private var ReportArrayList = ArrayList<Result>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val reportViewHolder = ReportViewHolder(LayoutInflater.from(parent.context).inflate(
                R.layout.item_report_list, parent, false))
        return reportViewHolder    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.bindWithView(this.ReportArrayList[position])

    }

    override fun getItemCount(): Int {
        return this.ReportArrayList.size
    }

    fun submitList(ReportList: List<Result>){
        this.ReportArrayList = ReportList as ArrayList<Result>
    }


}



