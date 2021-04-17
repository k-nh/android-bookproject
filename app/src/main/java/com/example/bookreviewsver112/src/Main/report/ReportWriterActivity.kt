package com.example.bookreviewsver112.src.report

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.config.BaseActivity
import com.example.bookreviewsver112.databinding.ActivityReportPostBinding
import com.example.bookreviewsver112.databinding.ActivityReportWriteBinding
import com.example.bookreviewsver112.src.Main.report.vision.TextExtractActivity
import com.example.bookreviewsver112.src.report.login.LoginMainActivity
import java.lang.Exception

class ReportWriterActivity : BaseActivity<ActivityReportWriteBinding>(ActivityReportWriteBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val saveButton = binding.btReportSave as Button
        val titleText = binding.etReportTitle as EditText
        val contentsText = binding.etReportContents as EditText
        val TextExtractButton = binding.btTextExtract

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

        TextExtractButton.setOnClickListener {
            val Intent = Intent(this, TextExtractActivity::class.java)
            startActivity(Intent)
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