package com.example.screen_lake.db.repository


import com.example.screen_lake.db.dao.OnboardingTrackerDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingTrackerRepository @Inject constructor(private val onboardingTrackerDao: OnboardingTrackerDao){
}