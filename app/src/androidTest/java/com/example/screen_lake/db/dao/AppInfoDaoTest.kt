package com.example.screen_lake.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.screen_lake.db.ScreenLakeDatabase
import com.example.screen_lake.enums.AppDistractions
import com.example.screen_lake.models.AppInfo
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class AppInfoDaoTest {
    private lateinit var appInfoDao: AppInfoDao
    private lateinit var db:ScreenLakeDatabase

    private val appInfoList:List<AppInfo> = listOf(
        AppInfo(apk = "com.google.chrome","Chrome",AppDistractions.DISTRACTING.key),
        AppInfo(apk = "com.google.gmail","Gmail",AppDistractions.VERY_DISTRACTING.key),
        AppInfo(apk = "com.camera.camera","Camera",AppDistractions.NOT_A_PROBLEM.key),
        AppInfo(apk = "com.meta.snapchat","Snapchat",AppDistractions.DISTRACTING.key),
        AppInfo(apk = "com.meta.facebook","Facebook",AppDistractions.DISTRACTING.key),
        AppInfo(apk = "com.meta.instagram","Instagram",AppDistractions.VERY_DISTRACTING.key),
        )

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ScreenLakeDatabase::class.java
        ).build()
        appInfoDao = db.appInfoDao()
    }
    @Test
    fun checkEmptyTableWhenNoDataInserted()= runTest {
        val result =appInfoDao.getAllAppInfoList()
        assertThat(result.isEmpty()).isTrue()
    }
    @Test
    fun insertItemIsSuccessfullySaved()= runTest {
        appInfoList.forEach {
         appInfoDao.insertInstalledAppInfo(it)
        }
        val result = appInfoDao.getAllAppInfoList()
        assertThat(result.size).isEqualTo(appInfoList.size)
    }

    @Test
    fun checkIfGetInfoByNameResultIsCorrect()= runTest {
        val name = "com.google.chrome"
        appInfoList.forEach {
            appInfoDao.insertInstalledAppInfo(it)
        }
        val result = appInfoDao.getAllAppInfoByPackageName(name)
        assertThat(result!=null && result.apk.equals(name)).isTrue()
    }

    @Test
    fun checkIfGetInfoByWrongNameResultIsCorrect()= runTest {
        val name = "com.bang.chrome"
        appInfoList.forEach {
            appInfoDao.insertInstalledAppInfo(it)
        }
        val result = appInfoDao.getAllAppInfoByPackageName(name)
        assertThat(result==null).isTrue()
    }

    @Test
    fun checkListContainsOnlyDistractingItems()= runTest {
        val distractions = listOf<String>(AppDistractions.DISTRACTING.key)
        appInfoList.forEach {
            appInfoDao.insertInstalledAppInfo(it)
        }
        val result = appInfoDao.getAppInfoListWithDistractionItems(distractions)
        assertThat(result.size).isEqualTo(3)
    }

    @Test
    fun checkIfDistractionLevelUpdateCorrectly()= runTest {
        appInfoList.forEach {
            appInfoDao.insertInstalledAppInfo(it)
        }
        appInfoDao.insertInstalledAppInfo(appInfoList.get(1).copy(distractionLevel = AppDistractions.NOT_A_PROBLEM.key))
        val listSize = appInfoDao.getAllAppInfoList().size
        val appInfo = appInfoDao.getAllAppInfoByPackageName(appInfoList.get(1).apk)
        val result = ((listSize==appInfoList.size) &&(appInfo?.distractionLevel==AppDistractions.NOT_A_PROBLEM.key))
        assertThat(result).isTrue()
    }

}