package com.example.smsstocktracker

import retrofit2.Retrofit

import retrofit2.http.GET
import retrofit2.http.Query

interface StockPriceService {

    suspend fun getDailyPrice(
        stockCode: String,
        tradeDate: String   // yyyy-MM-dd
    ): StockDailyPrice?
}




interface StockApi {

    @GET("price/daily")
    suspend fun getDailyPrice(
        @Query("code") code: String,
        @Query("date") date: String
    ): StockDailyPrice
}



