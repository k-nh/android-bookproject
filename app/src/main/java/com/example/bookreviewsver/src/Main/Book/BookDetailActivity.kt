package com.example.bookreviewsver.src.Main.Book

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.bumptech.glide.Glide
import com.example.application.config.MyApplication
import com.example.bookreviewsver.MainActivity
import com.example.bookreviewsver.R
import com.example.bookreviewsver.src.Main.Report.model.*
import com.example.bookreviewsver.src.home.*
import com.google.android.material.navigation.NavigationView
import de.hdodenhof.circleimageview.CircleImageView


class BookDetailActivity : AppCompatActivity() {

    private var mDrawerLayout: DrawerLayout? = null
    var userId: String? = MyApplication.sSharedPreferences.getString("userId",null)
    var userProfileImg: String? = MyApplication.sSharedPreferences.getString("userProfileImg",null)


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_book_detail)
        val toolbar: Toolbar = findViewById<Toolbar>(R.id.detail_toolbar)
        setSupportActionBar(toolbar)
        val actionBar: ActionBar? = supportActionBar
        actionBar?.setDisplayShowTitleEnabled(false) // 기존 title 지우기
        actionBar?.setDisplayHomeAsUpEnabled(true)
        actionBar?.setHomeAsUpIndicator(R.drawable.ic_baseline_dehaze_24)

        mDrawerLayout = findViewById<DrawerLayout>(R.id.drawer_layout)

        supportFragmentManager.beginTransaction().replace(R.id.fragment2, BookDetailFragment()).commitAllowingStateLoss()
        val navigationView: NavigationView = findViewById<NavigationView>(R.id.nav_view)
        val header = navigationView.getHeaderView(0)
        var drawerId = header.findViewById<TextView>(R.id.drawer_id)
        drawerId.text = userId
        var drawerImg = header.findViewById<CircleImageView>(R.id.drawer_profile)
        Glide.with(this@BookDetailActivity)
            .load(userProfileImg).into(drawerImg)

        navigationView.setNavigationItemSelectedListener(object :
            NavigationView.OnNavigationItemSelectedListener {
            override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {

                menuItem.isChecked = true
                mDrawerLayout?.closeDrawers()
                val id: Int = menuItem.itemId
                val title: String = menuItem.title.toString()

                if (id == R.id.BookDetailFragment) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment2, BookDetailFragment())
                        .commitAllowingStateLoss()
                }
                else if (id == R.id.CBreportFragment) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment2, CBReportFragment())
                        .commitAllowingStateLoss()
                } else if (id == R.id.CBchatFragment) {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment2, CBChatFragment())
                        .commitAllowingStateLoss()
                }else if (id == R.id.mainActivity) {
                    val intent = Intent(this@BookDetailActivity, MainActivity::class.java)
                    startActivity(intent)
                }
                return true
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                // 왼쪽 상단 버튼 눌렀을 때
                mDrawerLayout?.openDrawer(GravityCompat.END)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

}