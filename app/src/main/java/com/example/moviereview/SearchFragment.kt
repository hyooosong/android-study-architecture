package com.example.moviereview

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import com.example.moviereview.databinding.FragmentSearchBinding

class SearchFragment : Fragment() {
    private lateinit var binding: FragmentSearchBinding
    private lateinit var adapter: SearchAdapter
    private var list = mutableListOf<String>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchBinding.inflate(inflater, container, false)
        initRecyclerView()
        setData()
        return binding.root
    }

    private fun initRecyclerView() {
        adapter = SearchAdapter()
        binding.rcvSearch.adapter = adapter
    }

    private fun setData() {
        list.add("aaa")
        list.add("bbb")
        list.add("ccc")
        list.add("aaa")
        list.add("bbb")
        list.add("ccc")
        adapter.submitList(list)
    }
}