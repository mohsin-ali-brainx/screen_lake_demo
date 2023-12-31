package com.example.screen_lake.presentation.screens.onboarding.appListOnboarding

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ONE
import com.example.screen_lake.appUtils.Constants.IntegerConstants.TWO
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ZERO
import com.example.screen_lake.appUtils.Constants.StringConstants.EMPTY
import com.example.screen_lake.appUtils.Constants.TestTags.CUSTOM_EDIT_TEXT_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.ONBOARDING_BOTTOM_SHEET_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.SHOW_MORE_OR_LESS_TEST_TAG
import com.example.screen_lake.appUtils.enums.AppDistractions
import com.example.screen_lake.appUtils.enums.OnboardingTrackStep
import com.example.screen_lake.appUtils.enums.getAppDistractionFromKey
import com.example.screen_lake.domain.models.AppInfo
import com.example.screen_lake.domain.models.OnboardingTracker
import com.example.screen_lake.presentation.screens.onboarding.appListOnBoardingScreen.AppListMainBodyContent
import com.example.screen_lake.presentation.screens.onboarding.appListOnBoardingScreen.AppListOnboardingScreen
import com.example.screen_lake.presentation.theme.ScreenLakeTheme
import com.example.screen_lake.presentation.viewmodels.AppListOnBoardingScreenUiEvents
import com.example.screen_lake.presentation.viewmodels.AppListOnboardingScreenState
import com.google.common.truth.Truth
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class AppListOnboardingScreenTest {

    @get:Rule
    val composeTestRule =  createComposeRule()
    private lateinit var context: Context

    private val appInfoList:ArrayList<AppInfo> = arrayListOf()

    @Before
    fun init(){
        context = ApplicationProvider.getApplicationContext<Context>()
        appInfoList.apply {
            clear()
            addAll(
                listOf(
                    AppInfo(apk = "com.google.chrome","Chrome", AppDistractions.DISTRACTING.key),
                    AppInfo(apk = "com.google.gmail","Gmail", AppDistractions.VERY_DISTRACTING.key),
                    AppInfo(apk = "com.camera.camera","Camera", AppDistractions.NOT_A_PROBLEM.key),
                    AppInfo(apk = "com.meta.snapchat","Snapchat", AppDistractions.DISTRACTING.key),
                    AppInfo(apk = "com.meta.facebook","Facebook", AppDistractions.DISTRACTING.key),
                    AppInfo(apk = "com.meta.instagram","Instagram", AppDistractions.VERY_DISTRACTING.key),
                )
            )
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun BottomSheetIsExpanded_WhenOnBoardingTrackerStepIs__APP_LIST_BOTTOMSHEET_SCREEN_STEP(){
        composeTestRule.setContent {
            ScreenLakeTheme {
            AppListOnboardingScreen(
                navHostController = rememberNavController(),
                onboardingTracker = OnboardingTracker(ONE,started = false, OnboardingTrackStep.APP_LIST_BOTTOMSHEET_SCREEN_STEP.step),
                dataState = MutableStateFlow(AppListOnboardingScreenState()).asStateFlow() ,
                uiEvents = MutableSharedFlow<AppListOnBoardingScreenUiEvents>().asSharedFlow(),
                onEvent = {}
            )
            }
        }
        composeTestRule.onNodeWithTag(ONBOARDING_BOTTOM_SHEET_TEST_TAG).assertIsDisplayed()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun BottomSheetIsCollapsed_WhenOnBoardingTrackerStepIs__APP_LIST_SCREEN_STEP(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(
                        1,
                        started = true,
                        OnboardingTrackStep.APP_LIST_SCREEN_STEP.step
                    ),
                    dataState = MutableStateFlow(AppListOnboardingScreenState()).asStateFlow(),
                    uiEvents = MutableSharedFlow<AppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(ONBOARDING_BOTTOM_SHEET_TEST_TAG).assertIsNotDisplayed()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckSearchTextIsNotEmpty(){
        val chrome ="chrome"
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(
                        ONE,
                        started = true,
                        OnboardingTrackStep.APP_LIST_SCREEN_STEP.step
                    ),
                    dataState = MutableStateFlow(
                        AppListOnboardingScreenState(
                            searchText = chrome
                        )
                    ).asStateFlow(),
                    uiEvents = MutableSharedFlow<AppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(CUSTOM_EDIT_TEXT_TEST_TAG).assertTextEquals(chrome)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckLazyColumnIsEmpty(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListMainBodyContent(
                    bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
                    state = AppListOnboardingScreenState(
                    filteredList = emptyList(),
                    installedApps = emptyList()
                ), modifier = Modifier){

                }
            }
        }
        composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG).assertDoesNotExist()
    }
    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckLazyColumnNotEmpty(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListMainBodyContent(
                    bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
                    state = AppListOnboardingScreenState(
                    filteredList = appInfoList,
                    installedApps = appInfoList
                ), modifier = Modifier){

                }
            }
        }
        Truth.assertThat(composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG).onChildren()).isNotEqualTo(ZERO)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckLazyColumnIfDistractionVisible(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListMainBodyContent(
                    bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
                    state = AppListOnboardingScreenState(
                    filteredList = appInfoList,
                    installedApps = appInfoList
                ), modifier = Modifier){

                }
            }
        }
        composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG).onChildren()
        composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG).onChildren().assertAny(
            hasText(getAppDistractionFromKey(appInfoList[ZERO].distractionLevel?: EMPTY).distraction)
        )
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckSearchTextIsNotEmptyAndFilterItemIsCorrect(){
        val chrome ="Chrome"
        val filteredList = appInfoList.filter {
            it.realAppName.equals(chrome, ignoreCase = true)
        }
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(
                        ONE,
                        started = true,
                        OnboardingTrackStep.APP_LIST_SCREEN_STEP.step
                    ),
                    dataState = MutableStateFlow(
                        AppListOnboardingScreenState(
                            searchText = chrome, filteredList = filteredList, installedApps = appInfoList
                        )
                    ).asStateFlow(),
                    uiEvents = MutableSharedFlow<AppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onAllNodesWithText(chrome).assertCountEquals(2)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckSearchTextIsNotEmptyAndFilterItemIsIncorrect(){
        val chrome ="Chrome"
        val filteredList = appInfoList.filter {
            it.realAppName.equals(chrome, ignoreCase = true)
        }
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(
                        ONE,
                        started = true,
                        OnboardingTrackStep.APP_LIST_SCREEN_STEP.step
                    ),
                    dataState = MutableStateFlow(
                        AppListOnboardingScreenState(
                            searchText = chrome, filteredList = filteredList, installedApps = appInfoList
                        )
                    ).asStateFlow(),
                    uiEvents = MutableSharedFlow<AppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onAllNodesWithText(chrome).assertCountEquals(1)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckShowMoreText(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(
                        ONE,
                        started = true,
                        OnboardingTrackStep.APP_LIST_SCREEN_STEP.step
                    ),
                    dataState = MutableStateFlow(
                        AppListOnboardingScreenState()
                            .copy(
                                filteredList = appInfoList,
                                installedApps = appInfoList,
                                expandedList = false
                            )
                    ).asStateFlow(),
                    uiEvents = MutableSharedFlow<AppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(SHOW_MORE_OR_LESS_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SHOW_MORE_OR_LESS_TEST_TAG).assertTextEquals("Show more apps (${appInfoList.size-5})")
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckShowMoreTestWithNonEmptySearchText(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(
                        ONE,
                        started = true,
                        OnboardingTrackStep.APP_LIST_SCREEN_STEP.step
                    ),
                    dataState = MutableStateFlow(
                        AppListOnboardingScreenState()
                            .copy(
                               searchText = "chrome"
                            )
                    ).asStateFlow(),
                    uiEvents = MutableSharedFlow<AppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(SHOW_MORE_OR_LESS_TEST_TAG).assertDoesNotExist()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckShowMoreTestWithSmallSizeList(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                AppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(
                        ONE,
                        started = true,
                        OnboardingTrackStep.APP_LIST_SCREEN_STEP.step
                    ),
                    dataState = MutableStateFlow(
                        AppListOnboardingScreenState()
                            .copy(
                                installedApps = appInfoList.subList(ZERO, TWO)
                            )
                    ).asStateFlow(),
                    uiEvents = MutableSharedFlow<AppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(SHOW_MORE_OR_LESS_TEST_TAG).assertDoesNotExist()
    }
}