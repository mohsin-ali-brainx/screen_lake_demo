package com.example.screen_lake.di

import android.content.Context
import com.example.screen_lake.db.repository.AppInfoRepository
import com.example.screen_lake.db.repository.BehaviorRepository
import com.example.screen_lake.db.repository.OnboardingTrackerRepository
import com.example.screen_lake.repository.OnboardingRepository
import com.example.screen_lake.repository.OnboardingRepositoryImp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Singleton
    @Provides
    fun provideOnboardingRepository(
       @ApplicationContext context: Context,
       onboardingTrackerRepository: OnboardingTrackerRepository,
       appInfoRepository: AppInfoRepository,
       behaviorRepository: BehaviorRepository
    ) = OnboardingRepositoryImp(context,onboardingTrackerRepository,appInfoRepository, behaviorRepository) as OnboardingRepository
}