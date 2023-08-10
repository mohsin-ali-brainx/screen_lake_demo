package com.example.screen_lake.navigation

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.screen_lake.models.OnboardingTracker
import com.example.screen_lake.ui.screens.SplashScreen
import com.example.screen_lake.ui.screens.onboarding.appListOnboarding.AppListOnBoardingViewModel
import com.example.screen_lake.ui.screens.onboarding.appListOnboarding.AppListOnboardingScreen
import com.example.screen_lake.ui.screens.onboarding.behaviourOnboarding.BehaviorOnboardingScreen
import com.example.screen_lake.ui.screens.onboarding.behaviourOnboarding.BehaviorOnboardingViewModel
import com.example.screen_lake.ui.screens.onboarding.questions.occupation.OccupationQuestionnaireOnboardingScreen
import com.example.screen_lake.ui.screens.onboarding.questions.occupation.OccupationQuestionnaireViewModel
import com.example.screen_lake.ui.screens.onboarding.workAppsOnboarding.WorkAppListOnboardingScreen
import com.example.screen_lake.ui.screens.onboarding.workAppsOnboarding.WorkAppsOnboardingViewModel

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ScreenLakeNavGraph(navController: NavHostController,onboardingTracker:OnboardingTracker) {
    NavHost(
        navController = navController,
        startDestination = getScreenFromStep(onboardingTracker.step).route
    ) {
        composable(route = Screen.Splash.route) { backStackEntry ->
            SplashScreen(navController)
        }
        composable(route = Screen.AppListOnboardingScreenRoute.route) { backStackEntry ->
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
        composable(route=Screen.BehaviorOnboardingScreenRoute.route){
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
        composable(route=Screen.WorkAppsOnboardingScreenRoute.route){
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
        composable(route=Screen.OccupationScreenRoute.route){
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
}