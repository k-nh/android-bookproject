package com.example.bookreviewsver.src.home

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.ActionBar
import com.example.bookreviewsver.MainActivity
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentExtractBinding
import com.example.bookreviewsver.src.Main.OCR.ReportExtractionActivity

class ExtractFragment : BaseFragment<FragmentExtractBinding>(FragmentExtractBinding::bind, R.layout.fragment_extract){
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        (activity as MainActivity).supportActionBar?.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM)
        (activity as MainActivity).supportActionBar?.setCustomView(R.layout.toolbar_empty)

        binding.btnExtract.setOnClickListener {
            val intent = Intent(activity,ReportExtractionActivity::class.java)
            startActivity(intent)
        }
    }

}