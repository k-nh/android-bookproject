package com.example.myapplication

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import com.example.bookreviewsver.R
import com.example.bookreviewsver.databinding.FragmentLayoutBinding

class MyDialogFragment : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext()).apply {
            setTitle("Dialog Title")
            setPositiveButton("OK") { dialog, id -> println("OK") }
        }.create()
    }
}

class HomeFragment : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLayoutBinding.bind(view)
        binding.textView2.text = "HomeFragment"
    }
}

class Page1Fragment : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLayoutBinding.bind(view)
        binding.textView2.text = "Page1Fragment"
    }
}

class Page2Fragment : Fragment(R.layout.fragment_layout) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val binding = FragmentLayoutBinding.bind(view)
        binding.textView2.text = "Page2Fragment"
    }
}