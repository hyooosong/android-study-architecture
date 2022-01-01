package com.example.moviereview

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.example.moviereview.databinding.ActivityMainBinding
import com.google.android.material.tabs.TabLayoutMediator

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMainViewPager()
        initTabLayout()
    }

    private fun initMainViewPager() {
        binding.mainVp.apply {
            adapter = object : FragmentStateAdapter(this@MainActivity) {
                override fun getItemCount() = 2
                override fun createFragment(position: Int): Fragment {
                    return when(position) {
                        0 -> SearchFragment()
                        1 -> ReviewFragment()
                        else -> throw IndexOutOfBoundsException()
                    }
                }

            }
        }
    }

    private fun initTabLayout() {
        TabLayoutMediator(binding.mainTab, binding.mainVp) { tab, position ->
            when(position) {
                0 -> tab.text = "검색"
                1 -> tab.text = "리뷰"
            }
        }.attach()
    }
}