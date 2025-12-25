package com.example.smsstocktracker


object SmsAnalyzer {

    // 判断是否是推广短信（非常粗，但够用）
    fun isPromotionSms(text: String): Boolean {
        val keywords = listOf(
            "推荐", "个股", "涨停", "牛股", "操作建议", "建仓", "加仓"
        )
        return keywords.any { text.contains(it) }
    }

    // 提取机构来源（如【XX证券】）
    fun extractSource(text: String): String? {
        val regex = Regex("【([^】]{2,20})】")
        return regex.find(text)?.groupValues?.get(1)
    }

    // 提取股票代码（A股）
    fun extractStockCodes(text: String): List<String> {
        val regex = Regex("\\b(600|601|603|605|000|001|002|003|300|301|688)\\d{3}\\b")
        return regex.findAll(text).map { it.value }.toList()
    }
}
