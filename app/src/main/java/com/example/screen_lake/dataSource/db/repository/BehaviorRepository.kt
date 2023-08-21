package com.example.screen_lake.dataSource.db.repository

import com.example.screen_lake.dataSource.db.dao.BehaviorDao
import com.example.screen_lake.domain.models.Behavior
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
    suspend fun getBehaviourByName(name:String): Behavior?{
        return behaviorDao.getBehaviorByName(name)
    }
}