package com.example.bookreviewsver112.src.report

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.bookreviewsver112.config.BaseActivity
import com.example.bookreviewsver112.databinding.ActivityReportPostBinding
import java.lang.Exception

class ReportPostActivity : BaseActivity<ActivityReportPostBinding>(ActivityReportPostBinding::inflate){

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val titleText = binding.tvReportTitle as TextView
        val contentsText = binding.tvReportContents as TextView
        val modifyButton = binding.btReportModify as Button

        var titleData = ""
        var contentsData = ""

        var extras = intent.extras

        if (extras != null) {
            titleData = extras.getString("titleData").toString()
        }
        if (extras != null) {
            contentsData = extras.getString("contentsData").toString()
        }

        titleText.text = titleData
        contentsText.text = contentsData

        modifyButton.setOnClickListener {
            try{
                val intent = Intent(this,ReportWriterActivity::class.java)
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