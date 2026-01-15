package com.example.smsstocktracker.data

import android.util.Log

data class RecommendRecord(
    val source: String?,
    val stockCode: String,
    val recommendTime: Long,
    val rawText: String,
    val inputType: InputType
)

enum class InputType {
    MANUAL,
    NOTIFICATION
}

object RecommendRepository {

    // 内存中的推荐记录列表（临时方案，后面可换成数据库）
    private val records = mutableListOf<RecommendRecord>()

    /**
     * 新增一条推荐记录
     */
    fun add(record: RecommendRecord) {
        records.add(record)
        Log.d("SmsStock", "已保存推荐: $record")
    }

    /**
     * 获取全部推荐记录
     */
    fun getAll(): List<RecommendRecord> {
        return records.toList() // 返回副本，避免被外部修改
    }

    /**
     * 清空所有记录（调试用）
     */
    fun clear() {
        records.clear()
        Log.d("SmsStock", "所有推荐记录已清空")
    }
}
