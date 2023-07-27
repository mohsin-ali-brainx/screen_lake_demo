package com.example.screenlake.db.repository

import com.example.screenlake.db.dao.BehaviorDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BehaviorRepository @Inject constructor(private val behaviorDao: BehaviorDao){
}