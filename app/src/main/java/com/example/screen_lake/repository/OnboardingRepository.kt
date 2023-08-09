package com.example.screen_lake.repository

import android.content.Context
import android.content.pm.ApplicationInfo
import com.example.screen_lake.db.repository.AppInfoRepository
import com.example.screen_lake.db.repository.BehaviorRepository
import com.example.screen_lake.db.repository.OnboardingTrackerRepository
import com.example.screen_lake.extensions.getInstalledApps
import com.example.screen_lake.models.AppInfo
import com.example.screen_lake.models.Behavior
import com.example.screen_lake.models.GenericSelectionModel
import com.example.screen_lake.models.OnboardingTracker
import com.example.screen_lake.models.getAppBehaviorList
import com.example.screen_lake.models.getOccupationList
import com.example.screen_lake.models.getUpdatedBehaviorList
import com.example.screen_lake.models.toAppInfoList
import com.example.screen_lake.models.toWorkAppInfoList
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OnboardingRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val onboardingTrackerRepository: OnboardingTrackerRepository,
    private val appInfoRepository: AppInfoRepository,
    private val behaviorRepository: BehaviorRepository
) {

    suspend fun getInstalledAppList():ArrayList<Pair<ApplicationInfo,AppInfo>>{
        return getInstalledApps(context).toAppInfoList(context,appInfoRepository.getAppInfoList())
    }

    suspend fun getWorkAppList():ArrayList<Pair<ApplicationInfo,AppInfo>>{
        return getInstalledApps(context).toWorkAppInfoList(context)
    }

    suspend fun getAppBehaviourList():ArrayList<Behavior>{
        return getAppBehaviorList(context).getUpdatedBehaviorList(behaviorRepository.getBehaviorList())
    }

    suspend fun getOccupation():ArrayList<GenericSelectionModel>{
        return getOccupationList(context)
    }

    suspend fun insertOnboardingTracker(onboardingTracker: OnboardingTracker){
        withContext(Dispatchers.IO){
            onboardingTrackerRepository.insertOnboardingTracker(onboardingTracker)
        }
    }
    suspend fun deleteAllOnboardingTracker(){
        withContext(Dispatchers.IO){
            onboardingTrackerRepository.deleteAllOnboardingTracker()
        }
    }

    suspend fun getOnboardingTracker(): List<OnboardingTracker> {
        return onboardingTrackerRepository.getOnboardingTracker()
    }

    suspend fun insertAppInfo(appInfo: AppInfo){
        appInfoRepository.upsertAppInfo(appInfo)
    }

    suspend fun insertBehaviorInfo(behavior: Behavior){
        behaviorRepository.upsertBehavior(behavior)
    }

}