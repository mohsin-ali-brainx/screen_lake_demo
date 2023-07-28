package com.example.screen_lake.navigation

sealed class Screen(val route:String){
    object AppListOnboardingScreenRoute : Screen("app_list_onboarding_screen")
}
