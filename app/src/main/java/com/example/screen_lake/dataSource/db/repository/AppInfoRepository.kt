package com.example.screen_lake.dataSource.db.repository

import com.example.screen_lake.dataSource.db.dao.AppInfoDao
import com.example.screen_lake.appUtils.enums.AppDistractions
import com.example.screen_lake.domain.models.AppInfo
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInfoRepository @Inject constructor(private val appInfoDao: AppInfoDao){
    suspend fun insertAppInfo(appInfo: AppInfo){
        appInfoDao.insertInstalledAppInfo(appInfo)
    }
    suspend fun getAppInfoListWithDistractionItem():List<AppInfo>{
        return appInfoDao.getAppInfoListWithDistractionItems(listOf(
            AppDistractions.DISTRACTING.key,
            AppDistractions.VERY_DISTRACTING.key,
            AppDistractions.NOT_A_PROBLEM.key
        ))
    }
    suspend fun getAppInfoList():List<AppInfo>{
        return appInfoDao.getAllAppInfoList()
    }
    suspend fun getWorkAppInfoList():List<AppInfo>{
        return appInfoDao.getWorkAppInfoList()
    }

    suspend fun getAppInfoFromPackageName(packageName:String): AppInfo?{
        return appInfoDao.getAllAppInfoByPackageName(packageName)
    }
}