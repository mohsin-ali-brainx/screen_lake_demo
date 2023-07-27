package com.example.screenlake.db.repository

import com.example.screenlake.db.dao.OnboardingTrackerDao
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OnboardingTrackerRepository @Inject constructor(private val onboardingTrackerDao: OnboardingTrackerDao){
}