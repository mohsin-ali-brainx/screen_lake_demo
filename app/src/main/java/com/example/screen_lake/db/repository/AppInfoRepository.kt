package com.example.screen_lake.db.repository

import com.example.screen_lake.db.dao.AppInfoDao
import com.example.screen_lake.models.AppInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInfoRepository @Inject constructor(private val appInfoDao: AppInfoDao){
    suspend fun insertAppInfo(appInfo: AppInfo){
        appInfoDao.insertInstalledAppInfo(appInfo)
    }
    suspend fun getAppInfoListWithDistractionItem():List<AppInfo>{
        return appInfoDao.getAppInfoListWithDistractionItems()
    }
    suspend fun getAppInfoList():List<AppInfo>{
        return appInfoDao.getAllAppInfoList()
    }
    suspend fun getWorkAppInfoList():List<AppInfo>{
        return appInfoDao.getWorkAppInfoList()
    }

    suspend fun getAppInfoFromPackageName(packageName:String):AppInfo?{
        return appInfoDao.getAllAppInfoList(packageName)
    }
}