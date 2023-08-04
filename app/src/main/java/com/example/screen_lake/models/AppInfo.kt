package com.example.screen_lake.models

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.ApplicationInfo.CATEGORY_IMAGE
import android.content.pm.ApplicationInfo.CATEGORY_NEWS
import android.content.pm.ApplicationInfo.CATEGORY_PRODUCTIVITY
import android.content.pm.ApplicationInfo.CATEGORY_SOCIAL
import android.content.pm.ApplicationInfo.CATEGORY_VIDEO
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
    var isChecked:Boolean=false
){
    fun doesMatchSearchQuery(query:String):Boolean{
       return realAppName?.contains(query,true)?:false
    }
}

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

fun List<ApplicationInfo>.toWorkAppInfoList(context:Context):ArrayList<Pair<ApplicationInfo,AppInfo>>{
    val filteredList = ArrayList<Pair<ApplicationInfo,AppInfo>>().apply{
        clear()
        addAll(toAppInfoList(context).filter {
            it.first.category == CATEGORY_PRODUCTIVITY
                    ||it.first.category==CATEGORY_SOCIAL
                    ||it.first.category== CATEGORY_NEWS
                    ||it.first.category== CATEGORY_IMAGE
                    ||it.first.category== CATEGORY_VIDEO})
    }
    return filteredList
}

// CATEGORY_PRODUCTIVITY , CATEGORY_SOCIAL