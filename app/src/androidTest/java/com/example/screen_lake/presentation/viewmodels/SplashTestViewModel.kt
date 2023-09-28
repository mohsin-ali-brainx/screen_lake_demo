package com.example.screen_lake.presentation.viewmodels

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.example.screen_lake.appUtils.CoroutineTestRule
import com.example.screen_lake.dataSource.repositoryImp.OnboardingRepositoryImp
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
class SplashTestViewModel {
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    val coroutineTestRule = CoroutineTestRule()

    @Mock
    lateinit var repository: OnboardingRepositoryImp

    @Before
    fun setup(){
        MockitoAnnotations.openMocks(this)
    }

    @After
    fun tearDown(){

    }
}