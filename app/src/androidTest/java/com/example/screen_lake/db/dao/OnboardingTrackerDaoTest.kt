package com.example.screen_lake.db.dao

import androidx.test.filters.SmallTest
import com.example.screen_lake.db.ScreenLakeDatabase
import com.example.screen_lake.enums.OnboardingTrackStep
import com.example.screen_lake.models.OnboardingTracker
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
@HiltAndroidTest
@SmallTest
class OnboardingTrackerDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var onboardingTrackerDao: OnboardingTrackerDao

    @Inject
    @Named("test_database")
    lateinit var db:ScreenLakeDatabase

    @Before
    fun createDb(){
        hiltRule.inject()
        onboardingTrackerDao = db.onboardingTrackerDao()
    }

    @After
    fun closeDb(){
        db.close()
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