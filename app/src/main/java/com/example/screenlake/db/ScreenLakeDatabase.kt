package com.example.screenlake.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.screenlake.db.dao.AppInfoDao
import com.example.screenlake.db.dao.BehaviorDao
import com.example.screenlake.db.dao.OnboardingTrackerDao
import com.example.screenlake.db.dao.SocialMediaUserDao
import com.example.screenlake.models.AppInfo
import com.example.screenlake.models.Behavior
import com.example.screenlake.models.OnboardingTracker
import com.example.screenlake.models.SocialMediaUser
import com.example.screenlake.utils.Constants.IntegerConstants.ONE

@Database(entities = [OnboardingTracker::class,AppInfo::class,Behavior::class,SocialMediaUser::class],
    version = ONE,
    exportSchema = false
)
abstract class ScreenLakeDatabase:RoomDatabase(){
    abstract fun onboardingTrackerDao():OnboardingTrackerDao
    abstract fun appInfoDao():AppInfoDao
    abstract fun behaviorDao():BehaviorDao
    abstract fun socialMediaUserDao():SocialMediaUserDao
}