package com.example.screen_lake.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.screen_lake.R
import com.example.screen_lake.db.ScreenLakeDatabase
import com.example.screen_lake.enums.AppBehaviors
import com.example.screen_lake.models.Behavior
import com.google.common.truth.Truth
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class BehaviorDaoTest {
    private lateinit var context: Context
    private lateinit var behaviorDao: BehaviorDao
    private lateinit var db: ScreenLakeDatabase

    private val behaviorList:ArrayList<Behavior> = arrayListOf()

    @Before
    fun createDb(){
        context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ScreenLakeDatabase::class.java
        ).build()
        behaviorDao = db.behaviorDao()
        behaviorList.apply {
            clear()
            context.apply {
                add(Behavior(getString(R.string.doom_scrolling), AppBehaviors.WANT_LESS.importance))
                add(Behavior(getString(R.string.habitual_pick_ups),AppBehaviors.STOP_THIS.importance))
                add(Behavior(getString(R.string.gaming),AppBehaviors.NOT_A_PROBLEM.importance))
                add(Behavior(getString(R.string.watching_videos),AppBehaviors.WANT_LESS.importance))
                add(Behavior(getString(R.string.viewing_adult_content),AppBehaviors.WANT_LESS.importance))
                add(Behavior(getString(R.string.texting_too_much),AppBehaviors.STOP_THIS.importance))
                add(Behavior(getString(R.string.calling_too_much),AppBehaviors.WANT_LESS.importance))
            }
        }
    }

    @Test
    fun checkEmptyTableWhenNoDataInserted()= runTest {
        val result =behaviorDao.getBehaviorList()
        Truth.assertThat(result.isEmpty()).isTrue()
    }
    @Test
    fun insertItemIsSuccessfullySaved()= runTest {
        val list = behaviorList.subList(0,3)
        list.forEach {
            behaviorDao.insertBehaviorInfo(it)
        }
        val result = behaviorDao.getBehaviorList().size
        Truth.assertThat(result).isEqualTo(list.size)
    }

    @Test
    fun checkIfGetInfoByNameResultIsCorrect()= runTest {
        val name = context.getString(R.string.gaming)
        behaviorList.forEach {
            behaviorDao.insertBehaviorInfo(it)
        }
        val result = behaviorDao.getBehaviorByName(name)
        Truth.assertThat(result != null && result.name == name).isTrue()
    }

    @Test
    fun checkIfGetInfoByWrongNameResultIsCorrect()= runTest {
        val name = context.getString(R.string.app_name)
        behaviorList.forEach {
            behaviorDao.insertBehaviorInfo(it)
        }
        val result = behaviorDao.getBehaviorByName(name)
        Truth.assertThat(result == null).isTrue()
    }

    @Test
    fun checkIfBehaviorImportanceUpdatesCorrectly()= runTest {
        behaviorList.forEach {
            behaviorDao.insertBehaviorInfo(it)
        }
        behaviorDao.insertBehaviorInfo(behaviorList.get(1).copy(importance = AppBehaviors.NOT_A_PROBLEM.importance))
        val behavior = behaviorDao.getBehaviorByName(behaviorList.get(1).name)
        Truth.assertThat(behavior?.importance).isEqualTo(AppBehaviors.NOT_A_PROBLEM.importance)
    }


}