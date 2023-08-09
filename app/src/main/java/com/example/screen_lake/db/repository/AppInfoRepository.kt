package com.example.screen_lake.db.repository

import com.example.screen_lake.db.dao.AppInfoDao
import com.example.screen_lake.models.AppInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInfoRepository @Inject constructor(private val appInfoDao: AppInfoDao){
    suspend fun upsertAppInfo(appInfo: AppInfo){
        appInfoDao.insertInstalledAppInfo(appInfo)
    }
    suspend fun getAppInfoList():List<AppInfo>{
        return appInfoDao.getAppInfoList()
    }
}