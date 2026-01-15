package com.example.smsstocktracker.ui

import androidx.fragment.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.Gravity

import android.os.Bundle
import android.widget.TextView
class InputFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "手动输入页面（待实现）"
            gravity = Gravity.CENTER
        }
    }
}



class RecordsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "推荐记录列表（待实现）"
            gravity = Gravity.CENTER
        }
    }
}

class StatsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return TextView(requireContext()).apply {
            text = "统计概览（待实现）"
            gravity = Gravity.CENTER
        }
    }
}
