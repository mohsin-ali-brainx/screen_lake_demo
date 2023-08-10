package com.example.screen_lake.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.screen_lake.db.ScreenLakeDatabase
import org.junit.Before

class BehaviorDaoTest {
    private lateinit var behaviorDao: BehaviorDao
    private lateinit var db: ScreenLakeDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ScreenLakeDatabase::class.java
        ).build()
        behaviorDao = db.behaviorDao()
    }

}