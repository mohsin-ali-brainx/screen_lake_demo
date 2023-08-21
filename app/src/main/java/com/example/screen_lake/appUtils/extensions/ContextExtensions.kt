package com.example.screen_lake.appUtils.extensions

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo

fun getInstalledApps(context: Context):List<ApplicationInfo>{
    context.apply {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        val activities = packageManager.queryIntentActivities(mainIntent, 0)
        return activities.mapNotNull { it.activityInfo.applicationInfo }
    }
}