package com.example.bookreviewsver112

import android.os.Bundle
import com.example.bookreviewsver112.config.BaseActivity
import com.example.bookreviewsver112.databinding.ActivityMainBinding
import com.example.bookreviewsver112.src.home.FavoriteFragment
import com.example.bookreviewsver112.src.home.HomeFragment
import com.example.bookreviewsver112.src.home.MyPageFragment
import com.example.bookreviewsver112.src.home.RecommendFragment
import com.example.bookreviewsver112.src.report.ReportListFragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : BaseActivity<ActivityMainBinding>(ActivityMainBinding::inflate) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.beginTransaction().replace(R.id.frameLayout, HomeFragment()).commitAllowingStateLoss()

        binding.nvHomeBottom.setOnNavigationItemSelectedListener(
                BottomNavigationView.OnNavigationItemSelectedListener { item ->
                    when (item.itemId) {
                        R.id.menu_bottom_home -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, HomeFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }

                        R.id.menu_bottom_report -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, ReportListFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }

                        R.id.menu_bottom_favorite -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, FavoriteFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }

                        R.id.menu_bottom_recommend -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, RecommendFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }

                        R.id.menu_bottom_mypage -> {
                            supportFragmentManager.beginTransaction()
                                    .replace(R.id.frameLayout, MyPageFragment())
                                    .commitAllowingStateLoss()
                            return@OnNavigationItemSelectedListener true
                        }
                    }
                    false
                })
    }
}