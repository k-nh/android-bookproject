package com.example.bookreviewsver.src.Main.Book

import android.app.DatePickerDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.R
import com.example.bookreviewsver.config.BaseFragment
import com.example.bookreviewsver.databinding.FragmentBookDetailBinding
import com.example.bookreviewsver.src.Main.Book.model.CheckListResponse
import com.example.bookreviewsver.src.Main.Book.service.BookRetrofitManager
import com.example.bookreviewsver.src.Main.Home.RecommendAdapter
import com.example.bookreviewsver.src.Main.Home.`interface`.BOOKAPI
import com.example.bookreviewsver.src.Main.Home.`interface`.BookFragmentView
import com.example.bookreviewsver.src.Main.Home.`interface`.Constants
import com.example.bookreviewsver.src.Main.Home.`interface`.RESPONSE_STATE
import com.example.bookreviewsver.src.Main.Home.model.BestSellerData
import com.example.bookreviewsver.src.Main.Home.model.LikeResponse
import com.example.bookreviewsver.src.Main.Home.model.SearchData
import com.example.bookreviewsver.src.Main.Home.service.HomeRetrofitManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import kotlinx.android.synthetic.main.add_like_dialog_layout.*
import kotlinx.android.synthetic.main.add_like_dialog_layout.buttonCancel
import kotlinx.android.synthetic.main.add_like_dialog_layout.buttonOk
import kotlinx.android.synthetic.main.add_read_dialog_layout.*
import kotlinx.android.synthetic.main.add_reading_dialog_layout.*
import java.util.*

class BookDetailFragment() : BaseFragment<FragmentBookDetailBinding>(FragmentBookDetailBinding::bind, R.layout.fragment_book_detail),View.OnClickListener, BookFragmentView {
    private var bookId: Int = MyApplication.sSharedPreferences.getInt("bookId", 0)
    private var title: String? = null
    private var bookImgUrl : String? = null
    private var author : String? = null
    private var publisher : String? = null
    private var description : String? = null
    private var pubDate : String? = null
    private var link: String? = null
    private var categoryId=0
    var userId: String? = MyApplication.sSharedPreferences.getString("userId",null)
    var isbn: String? = MyApplication.sSharedPreferences.getString("isbn",null)
    private lateinit var recommendRecyclerViewAdapter : RecommendAdapter
    private var recommendRecyclerView: RecyclerView? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getBookDetail(isbn!!)
        BookRetrofitManager(this).getCheckList()
        recommendRecyclerView = view.findViewById<View>(R.id.rv_relate_book) as RecyclerView

        //status 없는 애들 클릭 리스너
        binding.btnLikeBook.setOnClickListener(this)
        binding.btnReadingBook.setOnClickListener(this)
        binding.btnReadBook.setOnClickListener(this)


    }

    private fun getBookDetail(isbn : String) {
        HomeRetrofitManager.instance.getBookDetail(
            key = BOOKAPI.CLIENT_ID,
            query = isbn,
            queryType = "isbn",
            output = "json"
        ) { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    //ArrayList 들어옴
                    Log.d(Constants.TAG, "getBookDetail api 호출 성공")
                    for(i in responseArrayList!!) {
                        bookId = i.itemId!!
                        title = i.title
                        bookImgUrl = i.coverImgUrl
                        author = i.author
                        publisher = i.publisher
                        description = i.description
                        pubDate = i.pubDate
                        link = i.link
                        categoryId = i.categoryId!!
                    }

                    Glide.with(this)
                        .load(bookImgUrl).into(binding.ivBookImgDetail)
                    binding.tvBookTitleDetail.text = title
                    binding.tvSearchBookAuthor.text = author
                    binding.tvSearchBookPublisher.text = publisher
                    binding.tvDescriptionDetail.text = description
                    binding.tvSearchBookPubdate.text = pubDate
                    binding.btnSite.setOnClickListener {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(link.toString()))
                        startActivity(intent)
                    }

                    getRecommendBook(categoryId)
                    MyApplication.sSharedPreferences.edit().putInt("bookId", bookId).apply()
                    MyApplication.sSharedPreferences.edit().putString("title", title).apply()
                    MyApplication.sSharedPreferences.edit().putString("bookImgUrl", bookImgUrl).apply()
                }
                RESPONSE_STATE.FAIL -> {
                    showCustomToast("호출 에러입니다")
                }

            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            binding.btnLikeBook.id -> {
                Log.d("like", "버튼")
                pressBookLike()
            }
            binding.btnReadingBook.id -> {
                Log.d("reading", "버튼")
                pressBookReading()

            }
            binding.btnReadBook.id -> {
                Log.d("read", "버튼")
                pressBookRead()
            }
        }
    }

    override fun postBookLikeSuccess(bookLikeResponse: LikeResponse) {
        TODO("Not yet implemented")
    }

    override fun postBookLikeFailure(message: String?) {
        TODO("Not yet implemented")
    }

    //눌렀던거 체크
    override fun getCheckListSuccess(checkListResponse: CheckListResponse) {
        for(i in checkListResponse.result){
            if(i.bookID == bookId){
                if(i.status == 0){
                    binding.btnLikeBook.setBackgroundResource(R.drawable.box_background)

                    binding.btnLikeBook.setOnClickListener {
                        binding.btnLikeBook.setBackgroundResource(R.drawable.before_round_shape)
                        BookRetrofitManager(this).postBookLike(isbn!!,bookId,title!!,bookImgUrl!!,categoryId,"")
                        var ft :FragmentTransaction = requireFragmentManager().beginTransaction()
                        ft.detach(this).attach(this).commit()
                    }
                    binding.btnReadingBook.setOnClickListener {
                        binding.btnLikeBook.setBackgroundResource(R.drawable.before_round_shape)
                        pressBookReading()
                        BookRetrofitManager(this).postBookLike(isbn!!,bookId,title!!,bookImgUrl!!,categoryId,"")
                    }

                    binding.btnReadBook.setOnClickListener {
                        binding.btnLikeBook.setBackgroundResource(R.drawable.before_round_shape)
                        pressBookRead()
                        BookRetrofitManager(this).postBookLike(isbn!!,bookId,title!!,bookImgUrl!!,categoryId,"")
                    }
                }else if (i.status == 1){
                    binding.btnReadingBook.setBackgroundResource(R.drawable.box_background)

                    binding.btnLikeBook.setOnClickListener {
                        binding.btnReadingBook.setBackgroundResource(R.drawable.before_round_shape)
                        pressBookLike()
                        BookRetrofitManager(this).postBookReading(userId!!,isbn!!,bookId,title!!,bookImgUrl!!,"")
                    }

                    binding.btnReadingBook.setOnClickListener {
                        binding.btnReadingBook.setBackgroundResource(R.drawable.before_round_shape)
                        BookRetrofitManager(this).postBookReading(userId!!,isbn!!,bookId,title!!,bookImgUrl!!,"")
                        var ft :FragmentTransaction = requireFragmentManager().beginTransaction()
                        ft.detach(this).attach(this).commit()
                    }

                    binding.btnReadBook.setOnClickListener {
                        binding.btnReadingBook.setBackgroundResource(R.drawable.before_round_shape)
                        pressBookRead()
                        BookRetrofitManager(this).postBookReading(userId!!,isbn!!,bookId,title!!,bookImgUrl!!,"")
                    }
                }else{
                    binding.btnReadBook.setBackgroundResource(R.drawable.box_background)

                    binding.btnLikeBook.setOnClickListener {
                        binding.btnReadBook.setBackgroundResource(R.drawable.before_round_shape)
                        pressBookLike()
                    }
                    binding.btnReadingBook.setOnClickListener {
                        binding.btnReadBook.setBackgroundResource(R.drawable.before_round_shape)
                        pressBookReading()
                        BookRetrofitManager(this).postBookRead(userId!!,isbn!!,bookId,title!!,bookImgUrl!!,"")
                    }

                    binding.btnReadBook.setOnClickListener {
                        binding.btnReadBook.setBackgroundResource(R.drawable.before_round_shape)
                        BookRetrofitManager(this).postBookRead(userId!!,isbn!!,bookId,title!!,bookImgUrl!!,"")
                        var ft :FragmentTransaction = requireFragmentManager().beginTransaction()
                        ft.detach(this).attach(this).commit()
                    }
                }
            }
        }
    }

    fun pressBookLike() {
        val dialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme)
        dialog.setContentView(R.layout.add_like_dialog_layout)
        dialog.setCanceledOnTouchOutside(false)
        dialog.create()
        dialog.show()
        dialog.buttonOk.setOnClickListener {
            binding.btnLikeBook.setBackgroundResource(R.drawable.box_background)
            var promise = dialog.editTextName?.text.toString()
            BookRetrofitManager(this).postBookLike(isbn!!,bookId, title!!, bookImgUrl!!, categoryId, promise)
            dialog.dismiss()
            var ft :FragmentTransaction = requireFragmentManager().beginTransaction()
            ft.detach(this).attach(this).commit()
        }
        dialog.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun pressBookReading(){
        val dialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme)
        dialog.setContentView(R.layout.add_reading_dialog_layout)
        dialog.setCanceledOnTouchOutside(false)
        dialog.create()
        dialog.show()
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        dialog.iv_reading_calendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),{_,year,month,day->
                dialog.tv_start_day.text = year.toString() + "/" + (month+1).toString()+ "/" + day.toString() },year,month,day)
            datePickerDialog.show()
        }
        dialog.buttonOk.setOnClickListener {
            binding.btnReadingBook.setBackgroundResource(R.drawable.box_background)
            var startday = dialog.tv_start_day.text.toString()
            BookRetrofitManager(this).postBookReading(userId!!,isbn!!,bookId,title!!,bookImgUrl!!,startday)
            dialog.dismiss()
            var ft :FragmentTransaction = requireFragmentManager().beginTransaction()
            ft.detach(this).attach(this).commit()
        }
        dialog.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    fun pressBookRead(){
        val dialog = BottomSheetDialog(requireContext(),R.style.AppBottomSheetDialogTheme)
        dialog.setContentView(R.layout.add_read_dialog_layout)
        dialog.setCanceledOnTouchOutside(false)
        dialog.create()
        dialog.show()
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        dialog.iv_read_calendar.setOnClickListener {
            val datePickerDialog = DatePickerDialog(requireContext(),{_,year,month,day->
                dialog.tv_end_day.text = year.toString() + "/" + (month+1).toString()+ "/" + day.toString() },year,month,day)
            datePickerDialog.show()
        }
        dialog.buttonOk.setOnClickListener {
            binding.btnReadBook.setBackgroundResource(R.drawable.box_background)
            var endDay = dialog.tv_end_day.text.toString()
            BookRetrofitManager(this).postBookRead(userId!!,isbn!!,bookId,title!!,bookImgUrl!!,endDay)
            dialog.dismiss()
            var ft :FragmentTransaction = requireFragmentManager().beginTransaction()
            ft.detach(this).attach(this).commit()
        }
        dialog.buttonCancel.setOnClickListener {
            dialog.dismiss()
        }
    }

    override fun getCheckListFailure(message: String?) {
        TODO("Not yet implemented")
    }

    private fun getRecommendBook(categoryId : Int) {
        HomeRetrofitManager.instance.getBestSeller(
            key = BOOKAPI.CLIENT_ID,
            categoryId = categoryId,
            output = "json"
        ) { responseState, responseArrayList ->
            when (responseState) {
                RESPONSE_STATE.OKAY -> {
                    //ArrayList 들어옴
                    Log.d(Constants.TAG, "getRecommendBook api 호출 성공")

                    this.recommendRecyclerViewAdapter = RecommendAdapter()
                    if (responseArrayList != null) {
                        this.recommendRecyclerViewAdapter.submitList(responseArrayList)
                    }

                    recommendRecyclerView!!.layoutManager = LinearLayoutManager(activity,
                        LinearLayoutManager.HORIZONTAL, false)
                    recommendRecyclerView!!.adapter = this.recommendRecyclerViewAdapter


                }
                RESPONSE_STATE.FAIL -> { showCustomToast("호출 에러입니다") }

            }
        }
    }



}