package com.example.screen_lake.extensions

import android.content.Intent
import android.content.pm.ApplicationInfo
import com.example.screen_lake.ScreenLakeApp

fun getInstalledApps():List<ApplicationInfo>{
    ScreenLakeApp.getApplication().apply {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val activities = packageManager.queryIntentActivities(mainIntent, 0)
        return activities.mapNotNull { it.activityInfo.applicationInfo }
    }
}