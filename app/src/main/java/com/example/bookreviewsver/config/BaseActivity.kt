package com.example.bookreviewsver.config

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import android.app.Dialog
import android.content.Context
import androidx.fragment.app.Fragment
import com.example.bookreviewsver.R


abstract class BaseActivity<B: ViewBinding>(private val inflate: (LayoutInflater)-> B):
    AppCompatActivity() {
    protected lateinit var binding : B
        private set
    lateinit var mLoadingDialog : Dialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun showCustomToast(message: String?) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }

    fun showLoadingDialog(context: Context) {
        mLoadingDialog = Dialog(context)
        mLoadingDialog.show()
    }

    fun hideLoadingDialog() {
        if (mLoadingDialog.isShowing) {
            mLoadingDialog.dismiss()
        }
    }




}