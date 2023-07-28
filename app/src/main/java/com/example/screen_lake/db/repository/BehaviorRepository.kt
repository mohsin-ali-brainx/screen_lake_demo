package com.example.screen_lake.db.repository

import com.example.screen_lake.db.dao.BehaviorDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BehaviorRepository @Inject constructor(private val behaviorDao: BehaviorDao){
}