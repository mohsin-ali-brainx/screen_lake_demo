package com.example.screen_lake.extensions

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager

fun Context.getInstalledAppsList():List<ApplicationInfo>{
    return packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
}