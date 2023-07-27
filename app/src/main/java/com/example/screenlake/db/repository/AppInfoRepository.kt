package com.example.screenlake.db.repository

import com.example.screenlake.db.dao.AppInfoDao
import com.example.screenlake.db.dao.OnboardingTrackerDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppInfoRepository @Inject constructor(private val appInfoDao: AppInfoDao){
}