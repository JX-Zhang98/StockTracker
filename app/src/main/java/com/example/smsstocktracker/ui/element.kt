package com.example.smsstocktracker.ui

import androidx.fragment.app.Fragment
import android.view.ViewGroup
import android.view.LayoutInflater
import android.view.View
import android.view.Gravity

import android.os.Bundle
import android.widget.TextView

import android.util.Log

import android.widget.Button
import android.widget.EditText
import com.example.smsstocktracker.R
import com.example.smsstocktracker.analyzer.SmsAnalyzer
import com.example.smsstocktracker.data.InputType
import com.example.smsstocktracker.data.RecommendRecord
import com.example.smsstocktracker.data.RecommendRepository
class InputFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.fragment_input, container, false)

        // 1️⃣ 找控件
        val editSms = view.findViewById<EditText>(R.id.editSms)
        val btnAnalyze = view.findViewById<Button>(R.id.btnAnalyze)
        val textStatus = view.findViewById<TextView>(R.id.textStatus)

        // 2️⃣ 绑定点击逻辑
        btnAnalyze.setOnClickListener {

            val text = editSms.text.toString()

            if (text.isBlank()) {
                textStatus.text = "请输入短信内容"
                return@setOnClickListener
            }

            if (!SmsAnalyzer.isPromotionSms(text)) {
                textStatus.text = "非推广短信"
                return@setOnClickListener
            }

            val source = SmsAnalyzer.extractSource(text)
            val stocks = SmsAnalyzer.extractStockCodes(text)

            if (stocks.isEmpty()) {
                textStatus.text = "未识别到股票代码"
                return@setOnClickListener
            }

            stocks.forEach { code ->
                val record = RecommendRecord(
                    source = source,
                    stockCode = code,
                    recommendTime = System.currentTimeMillis(),
                    rawText = text,
                    inputType = InputType.MANUAL
                )
                RecommendRepository.add(record)
            }

            Log.d("SmsStock", "手动分析完成，${stocks.size} 条")
            textStatus.text = "已保存 ${stocks.size} 条推荐"
        }

        return view
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
