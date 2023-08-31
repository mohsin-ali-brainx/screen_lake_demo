package com.example.screen_lake.presentation.navigation

import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
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

private const val animDurationMillis = 700
private typealias SlideDirection = AnimatedContentTransitionScope.SlideDirection

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun ScreenLakeNavGraph(navController: NavHostController,onboardingTracker: OnboardingTracker) {
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.SPLASH.route
    ) {
        horizontallyAnimatedComposableEnterOnly(Screen.Splash.route){
            val viewModel = hiltViewModel<SplashViewModel>()
            SplashScreen(navController,onboardingTracker,viewModel.eventFlow)
        }
        horizontallyAnimatedComposable( Screen.AppListOnboardingScreenRoute.route){
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
        horizontallyAnimatedComposable( Screen.BehaviorOnboardingScreenRoute.route){
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
        horizontallyAnimatedComposable( Screen.WorkAppsOnboardingScreenRoute.route){
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
        horizontallyAnimatedComposable( Screen.OccupationScreenRoute.route){
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