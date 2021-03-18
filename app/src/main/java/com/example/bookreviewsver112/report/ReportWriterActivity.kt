package com.example.bookreviewsver112.report

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.R
import com.example.bookreviewsver112.databinding.ActivityReportWriteBinding
import org.w3c.dom.Text
import java.lang.Exception

class ReportWriterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityReportWriteBinding.inflate(layoutInflater)

        val saveButton = binding.btReportSave as Button
        val titleText = binding.etReportTitle as EditText
        val contentsText = binding.etReportContents as EditText

        var titleData = ""
        var contentsData = ""

        var extras = intent.extras

        if (extras != null) {
            titleData = extras.getString("titleData").toString()
            titleText.setText(titleData)
        }
        if (extras != null) {
            contentsData = extras.getString("contentsData").toString()
            contentsText.setText(contentsData)
        }

        saveButton!!.setOnClickListener {
            try{
                //*************
                //데이터베이스에 데이터 저장
                val titleData = titleText!!.text.toString()
                //제목 데이터 저장
                val contentsData = contentsText!!.text.toString()
                //내용 데이터 저장

                //ReportPostActivity 로 전환
                val intent = Intent(this,ReportPostActivity::class.java)
                intent.putExtra("titleData",titleData)
                intent.putExtra("contentsData",contentsData)
                startActivity(intent)

            }catch(e: Exception){
                e.printStackTrace()
            }

        }
        setContentView(binding.root)
    }

}