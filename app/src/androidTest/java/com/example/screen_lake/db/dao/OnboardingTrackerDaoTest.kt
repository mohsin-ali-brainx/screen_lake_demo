package com.example.screen_lake.db.dao

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.screen_lake.db.ScreenLakeDatabase
import com.example.screen_lake.enums.OnboardingTrackStep
import com.example.screen_lake.models.OnboardingTracker
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class OnboardingTrackerDaoTest {
    private lateinit var onboardingTrackerDao: OnboardingTrackerDao
    private lateinit var db: ScreenLakeDatabase

    @Before
    fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context,
            ScreenLakeDatabase::class.java
        ).build()
        onboardingTrackerDao = db.onboardingTrackerDao()
    }

    @Test
    fun empty_onboarding_object_save_splash_screen_step() = runTest{
        onboardingTrackerDao.insertOnboardingTracker(OnboardingTracker())
        val onboardingTrackerResult = onboardingTrackerDao.getOnboardingTracker().first()
        assertThat(onboardingTrackerResult.step).isEqualTo(OnboardingTrackStep.SPLASH_SCREEN_STEP.step)
    }

    @Test
    fun empty_table_before_inserting_data()= runTest {
        val onboardingTrackerResult = onboardingTrackerDao.getOnboardingTracker()
        assertThat(onboardingTrackerResult.isEmpty()).isTrue()
    }

    @Test
    fun not_empty_table_after_inserting_data()= runTest {
        onboardingTrackerDao.insertOnboardingTracker(OnboardingTracker())
        onboardingTrackerDao.insertOnboardingTracker(OnboardingTracker())
        val onboardingTrackerResult = onboardingTrackerDao.getOnboardingTracker()
        assertThat(onboardingTrackerResult.isEmpty()).isFalse()
    }

    @Test
    fun two_rows_should_not_created_with_same_ids()= runTest{
        onboardingTrackerDao.insertOnboardingTracker(OnboardingTracker(id=1,step = OnboardingTrackStep.SPLASH_SCREEN_STEP.step))
        onboardingTrackerDao.insertOnboardingTracker(OnboardingTracker(id=1,step = OnboardingTrackStep.APP_LIST_BOTTOMSHEET_SCREEN_STEP.step))
        val onboardingTrackerResult = onboardingTrackerDao.getOnboardingTracker()
        assertThat(onboardingTrackerResult.size).isEqualTo(1)
    }

    @Test
    fun onboarding_tracker_steps_increment_correctly_for_same_id() = runTest {
        val onboardingTracker = OnboardingTracker(id=1,step = OnboardingTrackStep.SPLASH_SCREEN_STEP.step)
        onboardingTrackerDao.insertOnboardingTracker(onboardingTracker)
        onboardingTrackerDao.insertOnboardingTracker(onboardingTracker.apply { step++ })
        onboardingTrackerDao.insertOnboardingTracker(onboardingTracker.apply { step++ })
        onboardingTrackerDao.insertOnboardingTracker(onboardingTracker.apply { step++ })
        val onboardingTrackerResult = onboardingTrackerDao.getOnboardingTracker().first()
        assertThat(onboardingTrackerResult.step).isEqualTo(OnboardingTrackStep.BEHAVIOUR_SCREEN_STEP.step)
    }
}