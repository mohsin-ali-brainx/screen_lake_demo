package com.example.screen_lake.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.screen_lake.models.AppInfo

@Dao
interface AppInfoDao {
    @Upsert
    suspend fun insertInstalledAppInfo(appInfo: AppInfo)
    @Query("SELECT * FROM app_info")
    suspend fun getAppInfoList():List<AppInfo>
}