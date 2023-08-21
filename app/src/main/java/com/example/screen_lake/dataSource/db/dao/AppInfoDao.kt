package com.example.screen_lake.dataSource.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.screen_lake.appUtils.enums.AppUse
import com.example.screen_lake.domain.models.AppInfo

@Dao
interface AppInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstalledAppInfo(appInfo: AppInfo)

    @Query("SELECT * FROM app_info WHERE apk=:packageName")
    suspend fun getAllAppInfoByPackageName(packageName:String): AppInfo?
    @Query("SELECT * FROM app_info")
    suspend fun getAllAppInfoList():List<AppInfo>
    @Query("SELECT * FROM app_info WHERE distractionLevel in (:distractions)")
    suspend fun getAppInfoListWithDistractionItems(distractions: List<String> = listOf()):List<AppInfo>

    @Query("SELECT * FROM app_info WHERE appPrimaryUse=:primaryUse")
    suspend fun getWorkAppInfoList(primaryUse: String = AppUse.WORK.key):List<AppInfo>
}