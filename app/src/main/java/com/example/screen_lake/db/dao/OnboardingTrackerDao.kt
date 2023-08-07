package com.example.screen_lake.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.screen_lake.models.OnboardingTracker

@Dao
interface OnboardingTrackerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOnboardingTracker(tracker: OnboardingTracker)
    @Query("DELETE FROM onboarding")
    suspend fun deleteAll()

    @Query("SELECT * FROM onboarding")
    fun getOnboardingTracker(): List<OnboardingTracker>

    @Query("SELECT * FROM onboarding")
    fun getOnboardingTracker2(): List<OnboardingTracker>
}