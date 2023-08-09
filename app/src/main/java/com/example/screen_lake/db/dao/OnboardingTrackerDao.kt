package com.example.screen_lake.db.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.screen_lake.models.OnboardingTracker

@Dao
interface OnboardingTrackerDao {
    @Upsert
    suspend fun insertOnboardingTracker(tracker: OnboardingTracker)
    @Query("DELETE FROM onboarding")
    suspend fun deleteAll()
    @Query("SELECT * FROM onboarding")
    fun getOnboardingTracker(): List<OnboardingTracker>
}