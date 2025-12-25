package com.example.smsstocktracker


data class StockDailyPrice(
    val stockCode: String,
    val tradeDate: String,   // yyyy-MM-dd
    val open: Double,
    val close: Double,
    val high: Double,
    val low: Double
)

data class RecommendPerformance(
    val stockCode: String,
    val source: String?,
    val recommendTime: Long,
    val periodDays: Int,

    val basePrice: Double,      // 推荐日收盘价
    val futurePrice: Double,    // 未来某日收盘价

    val changeRate: Double      // (future - base) / base
)

data class SourceStatistics(
    val source: String,
    val periodDays: Int,

    val totalCount: Int,
    val winCount: Int,

    val winRate: Double,        // winCount / totalCount
    val avgChangeRate: Double   // 平均涨跌幅
)



