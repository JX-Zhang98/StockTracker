package com.example.smsstocktracker

import retrofit2.http.GET
import retrofit2.http.Query

data class StockInfo(
    val code: String,
    val name: String
)

interface StockApi {
    @GET("price/daily")
    suspend fun getDailyPrice(
        @Query("code") code: String,
        @Query("date") date: String
    ): StockDailyPrice

    // 新增：股票信息查询接口
    @GET("stock/search")
    suspend fun searchStock(
        @Query("keyword") keyword: String
    ): StockInfo
}




