package com.example.screen_lake.di

import android.app.Application
import androidx.room.Room
import com.example.screen_lake.db.ScreenLakeDatabase
import com.example.screen_lake.db.dao.AppInfoDao
import com.example.screen_lake.db.dao.BehaviorDao
import com.example.screen_lake.db.dao.OnboardingTrackerDao
import com.example.screen_lake.db.dao.SocialMediaUserDao
import com.example.screenlake.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Singleton
    @Provides
    fun provideDatabase(app: Application): ScreenLakeDatabase {
        return Room.databaseBuilder(app, ScreenLakeDatabase::class.java,Constants.DBConstants.SCREEN_LAKE_DB)
            .build()
    }

    @Singleton
    @Provides
    fun provideOnboardingTrackerDao(db: ScreenLakeDatabase): OnboardingTrackerDao {
        return db.onboardingTrackerDao()
    }

    @Singleton
    @Provides
    fun provideAppInfoDao(db: ScreenLakeDatabase): AppInfoDao {
        return db.appInfoDao()
    }

    @Singleton
    @Provides
    fun provideBehaviorDao(db: ScreenLakeDatabase): BehaviorDao {
        return db.behaviorDao()
    }

    @Singleton
    @Provides
    fun provideSocialMediaUserDao(db: ScreenLakeDatabase): SocialMediaUserDao {
        return db.socialMediaUserDao()
    }
}