package com.example.screen_lake.dataSource.db.repository


import com.example.screen_lake.dataSource.db.dao.OnboardingTrackerDao
import com.example.screen_lake.domain.models.OnboardingTracker
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingTrackerRepository @Inject constructor(private val onboardingTrackerDao: OnboardingTrackerDao){
    suspend fun insertOnboardingTracker(onboardingTracker: OnboardingTracker){
        onboardingTrackerDao.insertOnboardingTracker(onboardingTracker)
    }

    suspend fun getOnboardingTracker(): List<OnboardingTracker> {
      return onboardingTrackerDao.getOnboardingTracker()
    }
}