package com.example.screen_lake.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.screen_lake.db.dao.AppInfoDao
import com.example.screen_lake.db.dao.BehaviorDao
import com.example.screen_lake.db.dao.OnboardingTrackerDao
import com.example.screen_lake.db.dao.SocialMediaUserDao
import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.models.Behavior
import com.example.screen_lake.models.BitmapConverter
import com.example.screen_lake.models.OnboardingTracker
import com.example.screen_lake.models.SocialMediaUser
import com.example.screenlake.utils.Constants.IntegerConstants.ONE

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