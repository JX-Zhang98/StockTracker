package com.example.smsstocktracker.service

import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log

class StockNotificationListener : NotificationListenerService() {

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        val pkg = sbn.packageName
        val extras = sbn.notification.extras

        val title = extras.getCharSequence("android.title")?.toString()
        val text = extras.getCharSequence("android.text")?.toString()

        Log.d("StockNotify", "包名: $pkg")
        Log.d("StockNotify", "标题: $title")
        Log.d("StockNotify", "内容: $text")
    }
}
