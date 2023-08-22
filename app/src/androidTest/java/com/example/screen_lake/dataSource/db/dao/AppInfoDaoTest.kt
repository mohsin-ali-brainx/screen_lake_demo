package com.example.screen_lake.dataSource.db.dao

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.screen_lake.dataSource.db.ScreenLakeDatabase
import com.example.screen_lake.appUtils.enums.AppDistractions
import com.example.screen_lake.domain.models.AppInfo
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ONE
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class AppInfoDaoTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var appInfoDao: AppInfoDao

    @Inject
    @Named("test_database")
    lateinit var db: ScreenLakeDatabase

    private val appInfoList:List<AppInfo> = listOf(
        AppInfo(apk = "com.google.chrome","Chrome", AppDistractions.DISTRACTING.key),
        AppInfo(apk = "com.google.gmail","Gmail", AppDistractions.VERY_DISTRACTING.key),
        AppInfo(apk = "com.camera.camera","Camera", AppDistractions.NOT_A_PROBLEM.key),
        AppInfo(apk = "com.meta.snapchat","Snapchat", AppDistractions.DISTRACTING.key),
        AppInfo(apk = "com.meta.facebook","Facebook", AppDistractions.DISTRACTING.key),
        AppInfo(apk = "com.meta.instagram","Instagram", AppDistractions.VERY_DISTRACTING.key),
        )

    @Before
    fun createDb(){
        hiltRule.inject()
        appInfoDao = db.appInfoDao()
    }

    @After
    fun closeDb(){
        db.close()
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
        assertThat(result).isEqualTo(appInfoList)
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
        result.forEach {
            assertThat(it.distractionLevel).isEqualTo(AppDistractions.DISTRACTING.key)
        }
    }

    @Test
    fun checkIfDistractionLevelUpdateCorrectly()= runTest {
        appInfoList.forEach {
            appInfoDao.insertInstalledAppInfo(it)
        }
        appInfoDao.insertInstalledAppInfo(appInfoList[ONE].copy(distractionLevel = AppDistractions.NOT_A_PROBLEM.key))
        val listSize = appInfoDao.getAllAppInfoList().size
        val appInfo = appInfoDao.getAllAppInfoByPackageName(appInfoList[ONE].apk)
        val result = ((listSize==appInfoList.size) &&(appInfo?.distractionLevel== AppDistractions.NOT_A_PROBLEM.key))
        assertThat(result).isTrue()
    }

}