package com.example.screen_lake.presentation.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import com.example.screen_lake.appUtils.enums.OnboardingTrackStep
import com.example.screen_lake.domain.models.OnboardingTracker
import com.example.screen_lake.presentation.screens.onboarding.appListOnBoardingScreen.AppListOnboardingScreen
import com.example.screen_lake.presentation.screens.onboarding.behaviourOnboarding.BehaviorOnboardingScreen
import com.example.screen_lake.presentation.screens.onboarding.workAppsOnboarding.WorkAppListOnboardingScreen
import com.example.screen_lake.presentation.screens.questions.occupation.OccupationQuestionnaireOnboardingScreen
import com.example.screen_lake.presentation.screens.splash.SplashScreen
import com.example.screen_lake.presentation.utils.horizontallyAnimatedComposable
import com.example.screen_lake.presentation.utils.horizontallyAnimatedComposableEnterOnly
import com.example.screen_lake.presentation.viewmodels.AppListOnBoardingViewModel
import com.example.screen_lake.presentation.viewmodels.BehaviorOnboardingViewModel
import com.example.screen_lake.presentation.viewmodels.OccupationQuestionnaireViewModel
import com.example.screen_lake.presentation.viewmodels.SplashViewModel
import com.example.screen_lake.presentation.viewmodels.WorkAppsOnboardingViewModel

enum class OnboardingScreenRoutes(val route: String){
    SPLASH("splash_screen"),
    APP_LIST_ONBOARDING("app_list_onboarding_screen"),
    BEHAVIOR_ONBOARDING("behavior_onboarding_screen"),
    WORK_APPS_ONBOARDING("work_apps_onboarding_screen"),
    OCCUPATION("occupation_screen")
}
sealed class OnboardingScreen(val route:String, val step:Int){
    object Splash : OnboardingScreen(OnboardingScreenRoutes.SPLASH.route, OnboardingTrackStep.SPLASH_SCREEN_STEP.step)
    object AppListOnboardingScreenRoute : OnboardingScreen(OnboardingScreenRoutes.APP_LIST_ONBOARDING.route, OnboardingTrackStep.APP_LIST_SCREEN_STEP.step)
    object BehaviorOnboardingScreenRoute : OnboardingScreen(OnboardingScreenRoutes.BEHAVIOR_ONBOARDING.route, OnboardingTrackStep.BEHAVIOUR_SCREEN_STEP.step)
    object WorkAppsOnboardingScreenRoute : OnboardingScreen(OnboardingScreenRoutes.WORK_APPS_ONBOARDING.route, OnboardingTrackStep.WORK_APP_SCREEN_STEP.step)
    object OccupationScreenRoute : OnboardingScreen(OnboardingScreenRoutes.OCCUPATION.route, OnboardingTrackStep.OCCUPATION_SCREEN_STEP.step)
}

fun getScreenFromStep(step: Int): OnboardingScreen {
   return when(step){
       OnboardingTrackStep.APP_LIST_BOTTOMSHEET_SCREEN_STEP.step, OnboardingTrackStep.APP_LIST_SCREEN_STEP.step-> OnboardingScreen.AppListOnboardingScreenRoute
       OnboardingTrackStep.BEHAVIOUR_SCREEN_STEP.step-> OnboardingScreen.BehaviorOnboardingScreenRoute
       OnboardingTrackStep.WORK_APP_SCREEN_STEP.step, OnboardingTrackStep.WORK_APP_BOTTOMSHEET_SCREEN_STEP.step-> OnboardingScreen.WorkAppsOnboardingScreenRoute
       OnboardingTrackStep.OCCUPATION_SCREEN_STEP.step-> OnboardingScreen.OccupationScreenRoute
       else-> OnboardingScreen.AppListOnboardingScreenRoute
    }
}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
fun NavGraphBuilder.onboardingNavGraph(navController: NavHostController, onboardingTracker: OnboardingTracker){
        horizontallyAnimatedComposableEnterOnly(OnboardingScreen.Splash.route){
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(navController,onboardingTracker,viewModel.eventFlow)
        }
        horizontallyAnimatedComposable(OnboardingScreen.AppListOnboardingScreenRoute.route){
            val viewModel = hiltViewModel<AppListOnBoardingViewModel>()
            AppListOnboardingScreen(
                navController,
                onboardingTracker,
                viewModel.state,
                viewModel.eventFlow
            ) {
                viewModel.onEventUpdate(it)
            }
        }
        horizontallyAnimatedComposable( OnboardingScreen.BehaviorOnboardingScreenRoute.route){
            val viewModel = hiltViewModel<BehaviorOnboardingViewModel>()
            BehaviorOnboardingScreen(
                navController,
                onboardingTracker,
                viewModel.state,
                viewModel.eventFlow
            ) {
                viewModel.onEventUpdate(it)
            }
        }
        horizontallyAnimatedComposable( OnboardingScreen.WorkAppsOnboardingScreenRoute.route){
            val viewModel = hiltViewModel<WorkAppsOnboardingViewModel>()
            WorkAppListOnboardingScreen(
                navController,
                onboardingTracker,
                viewModel.state,
                viewModel.eventFlow
            ){
                viewModel.onEventUpdate(it)
            }
        }
        horizontallyAnimatedComposable( OnboardingScreen.OccupationScreenRoute.route){
            val viewModel = hiltViewModel<OccupationQuestionnaireViewModel>()
            OccupationQuestionnaireOnboardingScreen(
                navController,
                viewModel.state,
                viewModel.eventFlow
            ){
                viewModel.onEventUpdate(it)
            }
        }
}
