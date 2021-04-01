package com.example.bookreviewsver112.src.report

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.bookreviewsver112.R

class ReportAdapter(
    private val item: ArrayList<ReportItem>?)
    : RecyclerView.Adapter<ReportAdapter.ReportViewHolder>() {

    class ReportViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var titleText: TextView
        var contentsText: TextView

        init {
            titleText = view.findViewById(R.id.tv_item_title) as TextView
            contentsText = view.findViewById(R.id.tv_item_contents) as TextView
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_report_list,parent,false)
        return ReportViewHolder(view)
    }

    override fun getItemCount(): Int {
        return item?.size ?: 0
    }

    override fun onBindViewHolder(holder: ReportViewHolder, position: Int) {
        holder.titleText.text = item!![position].titleText
        holder.contentsText.text = item!![position].contentsText
    }


}