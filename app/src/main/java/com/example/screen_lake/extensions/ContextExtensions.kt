package com.example.screen_lake.extensions

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

fun getInstalledApps(context: Context,categories: List<String> = listOf(Intent.CATEGORY_LAUNCHER)):List<ApplicationInfo>{
    context.apply {
        val mainIntent = Intent(Intent.ACTION_MAIN, null)
        mainIntent.addCategory(Intent.CATEGORY_LAUNCHER)
        with(Intent(Intent.ACTION_MAIN,null)){
            categories.forEach {
                addCategory(it)
            }
            return  packageManager.queryIntentActivities(this, android.content.pm.PackageManager.MATCH_DEFAULT_ONLY).mapNotNull { it.activityInfo.applicationInfo }
        }
    }
}