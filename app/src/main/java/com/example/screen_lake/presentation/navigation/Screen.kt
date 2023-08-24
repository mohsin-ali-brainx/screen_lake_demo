package com.example.screen_lake.presentation.navigation

import com.example.screen_lake.appUtils.enums.OnboardingTrackStep

enum class ScreenRoutes(val route: String){
    SPLASH("splash_screen"),
    APP_LIST_ONBOARDING("app_list_onboarding_screen"),
    BEHAVIOR_ONBOARDING("behavior_onboarding_screen"),
    WORK_APPS_ONBOARDING("work_apps_onboarding_screen"),
    OCCUPATION("occupation_screen")
}
sealed class Screen(val route:String,val step:Int){
    object Splash : Screen(ScreenRoutes.SPLASH.route, OnboardingTrackStep.SPLASH_SCREEN_STEP.step)
    object AppListOnboardingScreenRoute : Screen(ScreenRoutes.APP_LIST_ONBOARDING.route, OnboardingTrackStep.APP_LIST_SCREEN_STEP.step)
    object BehaviorOnboardingScreenRoute : Screen(ScreenRoutes.BEHAVIOR_ONBOARDING.route, OnboardingTrackStep.BEHAVIOUR_SCREEN_STEP.step)
    object WorkAppsOnboardingScreenRoute : Screen(ScreenRoutes.WORK_APPS_ONBOARDING.route, OnboardingTrackStep.WORK_APP_SCREEN_STEP.step)
    object OccupationScreenRoute : Screen(ScreenRoutes.OCCUPATION.route, OnboardingTrackStep.OCCUPATION_SCREEN_STEP.step)
}

fun getScreenFromStep(step: Int): Screen {
   return when(step){
       OnboardingTrackStep.APP_LIST_BOTTOMSHEET_SCREEN_STEP.step, OnboardingTrackStep.APP_LIST_SCREEN_STEP.step-> Screen.AppListOnboardingScreenRoute
       OnboardingTrackStep.BEHAVIOUR_SCREEN_STEP.step-> Screen.BehaviorOnboardingScreenRoute
       OnboardingTrackStep.WORK_APP_SCREEN_STEP.step, OnboardingTrackStep.WORK_APP_BOTTOMSHEET_SCREEN_STEP.step-> Screen.WorkAppsOnboardingScreenRoute
       OnboardingTrackStep.OCCUPATION_SCREEN_STEP.step-> Screen.OccupationScreenRoute
       else-> Screen.AppListOnboardingScreenRoute
    }
}
