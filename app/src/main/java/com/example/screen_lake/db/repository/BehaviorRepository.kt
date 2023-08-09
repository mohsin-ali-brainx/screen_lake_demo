package com.example.screen_lake.db.repository

import com.example.screen_lake.db.dao.BehaviorDao
import com.example.screen_lake.models.Behavior
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BehaviorRepository @Inject constructor(private val behaviorDao: BehaviorDao){
    suspend fun upsertBehavior(behavior: Behavior){
        behaviorDao.insertBehaviorInfo(behavior)
    }
    suspend fun getBehaviorList():List<Behavior>{
        return behaviorDao.getBehaviorList()
    }
}