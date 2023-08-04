package com.example.screen_lake.navigation

sealed class Screen(val route:String){
    object AppListOnboardingScreenRoute : Screen("app_list_onboarding_screen")
    object BehaviorOnboardingScreenRoute : Screen("behavior_onboarding_screen")
    object WorkAppsOnboardingScreenRoute : Screen("work_apps_onboarding_screen")
    object OccupationScreenRoute : Screen("occupation_screen")
}
