package com.example.screen_lake.models

import android.content.Context
import android.content.pm.ApplicationInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.screen_lake.enums.AppDistractions
import com.example.screenlake.utils.Constants.IntegerConstants.ZERO

@Entity("app_info")
data class AppInfo(
    @PrimaryKey(autoGenerate = false)
    var apk:String,
    var realAppName:String?=null,
    var distractionLevel:String?=null,
    var appPrimaryUser:String?=null,
    var bitmapResource:String?=null,
)

fun List<ApplicationInfo>.toAppInfoList(context:Context):ArrayList<Pair<ApplicationInfo,AppInfo>>{
    val newAppInfoList = ArrayList<Pair<ApplicationInfo,AppInfo>>()
    val packageManager = context.packageManager
    forEach {
        val appName = if (it.labelRes != ZERO)
            packageManager.getResourcesForApplication(it).getString(it.labelRes)
        else
            it.loadLabel(packageManager).toString()
        newAppInfoList.add(Pair(it, AppInfo(it.packageName,appName,AppDistractions.NOT_DEFINED.key)))
    }
    return newAppInfoList
}
