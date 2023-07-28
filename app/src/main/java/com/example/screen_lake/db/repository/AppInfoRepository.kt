package com.example.screen_lake.db.repository

import com.example.screen_lake.db.dao.AppInfoDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInfoRepository @Inject constructor(private val appInfoDao: AppInfoDao){
}