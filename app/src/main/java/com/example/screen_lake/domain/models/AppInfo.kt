package com.example.screen_lake.domain.models

import android.content.Context
import android.content.pm.ApplicationInfo
import android.content.pm.ApplicationInfo.CATEGORY_IMAGE
import android.content.pm.ApplicationInfo.CATEGORY_NEWS
import android.content.pm.ApplicationInfo.CATEGORY_PRODUCTIVITY
import android.content.pm.ApplicationInfo.CATEGORY_SOCIAL
import android.content.pm.ApplicationInfo.CATEGORY_VIDEO
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverter
import com.example.screen_lake.ScreenLakeApp
import com.example.screen_lake.appUtils.enums.AppDistractions
import com.example.screen_lake.appUtils.extensions.getAppIconBitmap
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ZERO
import com.example.screen_lake.appUtils.Constants.StringConstants.EMPTY
import java.io.ByteArrayOutputStream

@Entity("app_info")
data class AppInfo(
    @PrimaryKey(autoGenerate = false)
    var apk:String,
    var realAppName:String?=null,
    var distractionLevel:String?=null,
    var appPrimaryUse:String?=EMPTY,
    var bitmapResource: Bitmap?=null,
    var isChecked:Boolean=false,
){
    fun doesMatchSearchQuery(query:String):Boolean{
       return realAppName?.contains(query,true)?:false
    }
}

class BitmapConverter{
    @TypeConverter
    fun fromBitmap(bitmap: Bitmap?):ByteArray{
        val outputStream = ByteArrayOutputStream()
        bitmap?.compress(Bitmap.CompressFormat.PNG,100,outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun toBitmap(byteArray: ByteArray):Bitmap?{
       return BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
    }
}

fun List<ApplicationInfo>.toAppList(context: Context,savedApp:List<AppInfo>?=null):ArrayList<AppInfo>{
    val newAppInfoList = ArrayList<AppInfo>()
    val packageManager = context.packageManager
    forEach {
        val appName = if (it.labelRes != ZERO)
            packageManager.getResourcesForApplication(it).getString(it.labelRes)
        else
            it.loadLabel(packageManager).toString()
        val appInfo = savedApp?.firstOrNull {app-> app.apk==it.packageName }
        val iconBitmap = ScreenLakeApp.getContext().getAppIconBitmap(it.packageName)
        newAppInfoList.add(appInfo?: AppInfo(it.packageName,appName,
            AppDistractions.NOT_DEFINED.key, bitmapResource = iconBitmap))
    }
    return newAppInfoList
}

fun List<ApplicationInfo>.toWorkAppList(context:Context,savedWorkApp:List<AppInfo>?=null):ArrayList<AppInfo>{
    val filteredList = ArrayList<AppInfo>().apply{
        clear()
        toAppInfoList(context).filter {
            it.first.category == CATEGORY_PRODUCTIVITY
                    ||it.first.category==CATEGORY_SOCIAL
                    ||it.first.category== CATEGORY_NEWS
                    ||it.first.category== CATEGORY_IMAGE
                    ||it.first.category== CATEGORY_VIDEO
        }.forEach {
            add(it.second)
        }
    }
    filteredList.forEach {
        val appInfo = savedWorkApp?.firstOrNull {app-> app.appPrimaryUse!=null && app.apk==it.apk}
        with(it){
            appInfo?.apply {
                this@with.appPrimaryUse = appPrimaryUse
                this@with.isChecked = isChecked
            }
        }
    }
    return filteredList
}

fun List<ApplicationInfo>.toAppInfoList(context:Context,savedApp:List<AppInfo>?=null):ArrayList<Pair<ApplicationInfo, AppInfo>>{
    val newAppInfoList = ArrayList<Pair<ApplicationInfo, AppInfo>>()
    val packageManager = context.packageManager
    forEach {
        val appName = if (it.labelRes != ZERO)
            packageManager.getResourcesForApplication(it).getString(it.labelRes)
        else
            it.loadLabel(packageManager).toString()
        val appInfo = savedApp?.firstOrNull {app-> app.apk==it.packageName }
        val iconBitmap = ScreenLakeApp.getContext().getAppIconBitmap(it.packageName)
        newAppInfoList.add(Pair(it, appInfo?: AppInfo(it.packageName,appName,
            AppDistractions.NOT_DEFINED.key, bitmapResource = iconBitmap)))
    }
    return newAppInfoList
}