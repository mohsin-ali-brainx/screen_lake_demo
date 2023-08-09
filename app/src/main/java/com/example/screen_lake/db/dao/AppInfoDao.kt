package com.example.screen_lake.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.screen_lake.enums.AppDistractions
import com.example.screen_lake.enums.AppUse
import com.example.screen_lake.models.AppInfo

@Dao
interface AppInfoDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertInstalledAppInfo(appInfo: AppInfo)

    @Query("SELECT * FROM app_info WHERE apk=:packageName")
    suspend fun getAllAppInfoList(packageName:String):AppInfo?
    @Query("SELECT * FROM app_info")
    suspend fun getAllAppInfoList():List<AppInfo>
    @Query("SELECT * FROM app_info WHERE distractionLevel!=:distractions")
    suspend fun getAppInfoListWithDistractionItems(distractions: AppDistractions = AppDistractions.NOT_DEFINED):List<AppInfo>

    @Query("SELECT * FROM app_info WHERE appPrimaryUse=:primaryUse")
    suspend fun getWorkAppInfoList(primaryUse: String = AppUse.WORK.key):List<AppInfo>
}