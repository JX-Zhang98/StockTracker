package com.example.smsstocktracker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.content.Intent
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch

import com.example.smsstocktracker.ui.InputFragment
import com.example.smsstocktracker.ui.RecordsFragment
import com.example.smsstocktracker.ui.StatsFragment

import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("SmsStock", "=== APP STARTED ===")

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, InputFragment())
            .commit()

        bottomNav.setOnItemSelectedListener { item ->
            val fragment = when (item.itemId) {
                R.id.nav_input -> InputFragment()
                R.id.nav_records -> RecordsFragment()
                R.id.nav_stats -> StatsFragment()
                else -> null
            }

            fragment?.let {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.fragmentContainer, it)
                    .commit()
                true
            } ?: false
        }



//        btnTestDailyApi.setOnClickListener {
//            lifecycleScope.launch {
//
//                try {
//                    textStatus.text = "请求中..."
//
//                    val price = ApiClient.stockApi.getDailyPrice(
//                        code = "600519",
//                        date = "2024-01-05"
//                    )
//                    Log.d("DailyApiTest", "返回结果: $price")
//
//                    textStatus.text =
//                        "${price.stockCode} ${price.tradeDate}\n" +
//                                "开:${price.open} 收:${price.close}"
//
//                } catch (e: Exception) {
//
//                    Log.e("DailyApiTest", "请求失败", e)
//                    textStatus.text = "失败：${e.javaClass.simpleName}"
//                }
//            }
//        }


//        // 仅执行一次，获取权限后就不需要再执行
//        btnOpenNotifyPermission.setOnClickListener {
//            startActivity(
//                Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
//            )
//        }

//        btnAnalyze.setOnClickListener {
//
//            val text = editSms.text.toString().trim()
//
//            if (text.isEmpty()) {
//                textStatus.text = "状态：请输入推荐信息"
//                return@setOnClickListener
//            }
//
//            // 是否推广短信
//            if (!SmsAnalyzer.isPromotionSms(text)) {
//                textResult.text = "未识别为推广信息"
//                textStatus.text = "状态：分析完成"
//                return@setOnClickListener
//            }
//
//            val source = SmsAnalyzer.extractSource(text) ?: "未知来源"
//            val stocks = SmsAnalyzer.extractStockCodes(text)
//
//            if (stocks.isEmpty()) {
//                textResult.text = "已识别来源：$source\n但未识别到股票代码"
//                textStatus.text = "状态：分析完成"
//                return@setOnClickListener
//            }
//
//            // 显示分析结果（前端可见）
//            textResult.text = """
//        来源：$source
//        股票代码：${stocks.joinToString(", ")}
//    """.trimIndent()
//
//            // 保存历史推荐（功能 2）
//            stocks.forEach { code ->
//                RecommendRepository.add(
//                    RecommendRecord(
//                        source = source,
//                        stockCode = code,
//                        recommendTime = System.currentTimeMillis(),
//                        rawText = text,
//                        inputType = InputType.MANUAL
//                    )
//                )
//            }
//
//            textStatus.text = "状态：已保存 ${stocks.size} 条推荐"
//        }

    }
}
