package com.example.smsstocktracker

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

    private val records = mutableListOf<RecommendRecord>()

    fun add(record: RecommendRecord) {
        records.add(record)
        Log.d("SmsStock", "已保存推荐: $record")
    }

    fun getAll(): List<RecommendRecord> = records
}
