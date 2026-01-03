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


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.d("SmsStock", "=== APP STARTED ===")

        val editSms = findViewById<EditText>(R.id.editSms)
        val btnAnalyze = findViewById<Button>(R.id.btnAnalyze)
        val textStatus = findViewById<TextView>(R.id.textStatus)
        val btnOpenNotifyPermission = findViewById<Button>( R.id.btnOpenNotifyPermission)
        val btnTestDailyApi = findViewById<Button>(R.id.btnTestDailyApi)

        btnTestDailyApi.setOnClickListener {
            lifecycleScope.launch {

                try {
                    textStatus.text = "请求中..."

                    val price = ApiClient.stockApi.getDailyPrice(
                        code = "600519",
                        date = "2024-01-05"
                    )
                    Log.d("DailyApiTest", "返回结果: $price")

                    textStatus.text =
                        "${price.stockCode} ${price.tradeDate}\n" +
                                "开:${price.open} 收:${price.close}"

                } catch (e: Exception) {

                    Log.e("DailyApiTest", "请求失败", e)
                    textStatus.text = "失败：${e.javaClass.simpleName}"
                }
            }
        }


        // 仅执行一次，获取权限后就不需要再执行
        btnOpenNotifyPermission.setOnClickListener {
            startActivity(
                Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS")
            )
        }

        btnAnalyze.setOnClickListener {
            val text = editSms.text.toString()
            if (text.isBlank()) {
                textStatus.text = "请输入短信内容"
                return@setOnClickListener
            }

            val sb = StringBuilder()

            // Check promotion
            if (SmsAnalyzer.isPromotionSms(text)) {
                sb.append("⚠️ 疑似推广短信\n")
            } else {
                sb.append("✅ 看起来像正常短信\n")
            }

            // Extract Source
            val source = SmsAnalyzer.extractSource(text)
            val stocks = SmsAnalyzer.extractStockCodes(text)


            if (source != null) {
                sb.append("来源: $source\n")
            } else {
                sb.append("来源: 未知\n")
            }

            // Extract Stock Codes
            val codes = SmsAnalyzer.extractStockCodes(text)
            if (codes.isNotEmpty()) {
                sb.append("提及股票: ${codes.joinToString(", ")}\n")
            } else {
                sb.append("未发现股票代码\n")
            }

            textStatus.text = sb.toString()

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
            textStatus.text = "已保存 ${stocks.size} 条推荐"

            lifecycleScope.launch {
                try {
                    val price = ApiClient.stockApi.getDailyPrice(
                        code = "600519",
                        date = "2024-01-05"
                    )

                    Log.d("SmsStock", "价格返回: $price")
                    textStatus.text = "获取价格成功"

                } catch (e: Exception) {
                    Log.e("SmsStock", "请求失败", e)
                    textStatus.text = "获取价格失败"
                }
            }
        }
    }
}
