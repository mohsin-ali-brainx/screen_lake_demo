package com.example.screen_lake.repository

import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.models.Behavior
import com.example.screen_lake.models.GenericSelectionModel
import com.example.screen_lake.models.OnboardingTracker

interface OnboardingRepository {
    suspend fun getInstalledAppListWithDistraction():ArrayList<AppInfo>

    suspend fun getWorkAppList():ArrayList<AppInfo>

    suspend fun getAllAppInfoList():List<AppInfo>
    suspend fun getAppInfoFromPackageName(packageManager: String):AppInfo?

    suspend fun getAppBehaviourList():ArrayList<Behavior>

    suspend fun getAppBehaviorByName(name:String):Behavior?

    suspend fun getOccupation():ArrayList<GenericSelectionModel>

    suspend fun insertOnboardingTracker(onboardingTracker: OnboardingTracker)

    suspend fun getOnboardingTracker(): List<OnboardingTracker>

    suspend fun insertAppInfo(appInfo: AppInfo)

    suspend fun insertBehaviorInfo(behavior: Behavior)

}