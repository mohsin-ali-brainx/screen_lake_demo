package com.example.screen_lake.dataSource.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.screen_lake.dataSource.db.dao.AppInfoDao
import com.example.screen_lake.dataSource.db.dao.BehaviorDao
import com.example.screen_lake.dataSource.db.dao.OnboardingTrackerDao
import com.example.screen_lake.dataSource.db.dao.SocialMediaUserDao
import com.example.screen_lake.domain.models.AppInfo
import com.example.screen_lake.domain.models.Behavior
import com.example.screen_lake.domain.models.BitmapConverter
import com.example.screen_lake.domain.models.OnboardingTracker
import com.example.screen_lake.domain.models.SocialMediaUser
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ONE

@Database(entities = [OnboardingTracker::class, AppInfo::class, Behavior::class, SocialMediaUser::class],
    version = ONE,
    exportSchema = false
)
@TypeConverters(BitmapConverter::class)
abstract class ScreenLakeDatabase:RoomDatabase(){
    abstract fun onboardingTrackerDao(): OnboardingTrackerDao
    abstract fun appInfoDao(): AppInfoDao
    abstract fun behaviorDao(): BehaviorDao
    abstract fun socialMediaUserDao(): SocialMediaUserDao
}