package com.example.screen_lake.navigation

import com.example.screen_lake.enums.OnboardingTrackStep

sealed class Screen(val route:String,val step:Int){
    object Splash : Screen("splash_screen", OnboardingTrackStep.SPLASH_SCREEN_STEP.step)
    object AppListOnboardingScreenRoute : Screen("app_list_onboarding_screen",OnboardingTrackStep.APP_LIST_SCREEN_STEP.step)
    object BehaviorOnboardingScreenRoute : Screen("behavior_onboarding_screen",OnboardingTrackStep.BEHAVIOUR_SCREEN_STEP.step)
    object WorkAppsOnboardingScreenRoute : Screen("work_apps_onboarding_screen",OnboardingTrackStep.WORK_APP_SCREEN_STEP.step)
    object OccupationScreenRoute : Screen("occupation_screen",OnboardingTrackStep.OCCUPATION_SCREEN_STEP.step)
}

fun getScreenFromStep(step: Int):Screen{
   return when(step){
       OnboardingTrackStep.APP_LIST_BOTTOMSHEET_SCREEN_STEP.step,OnboardingTrackStep.APP_LIST_SCREEN_STEP.step->Screen.AppListOnboardingScreenRoute
       OnboardingTrackStep.BEHAVIOUR_SCREEN_STEP.step->Screen.BehaviorOnboardingScreenRoute
       OnboardingTrackStep.WORK_APP_SCREEN_STEP.step,OnboardingTrackStep.WORK_APP_BOTTOMSHEET_SCREEN_STEP.step->Screen.WorkAppsOnboardingScreenRoute
       OnboardingTrackStep.OCCUPATION_SCREEN_STEP.step->Screen.OccupationScreenRoute
       else->Screen.Splash
    }
}
