package com.example.screen_lake.presentation.screens.onboarding.workAppsOnboarding

import android.content.Context
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.rememberBottomSheetScaffoldState
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertAny
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.assertTextEquals
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.navigation.compose.rememberNavController
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.screen_lake.appUtils.Constants.IntegerConstants.FIVE
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ONE
import com.example.screen_lake.appUtils.Constants.IntegerConstants.TWO
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ZERO
import com.example.screen_lake.appUtils.Constants.TestTags.CHECKED_ICON_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.CUSTOM_EDIT_TEXT_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.ONBOARDING_BOTTOM_SHEET_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.ONBOARDING_NEXT_BUTTON_TEST_TAG
import com.example.screen_lake.appUtils.Constants.TestTags.SHOW_MORE_OR_LESS_TEST_TAG
import com.example.screen_lake.appUtils.enums.AppUse
import com.example.screen_lake.appUtils.enums.OnboardingTrackStep
import com.example.screen_lake.domain.models.AppInfo
import com.example.screen_lake.domain.models.OnboardingTracker
import com.example.screen_lake.presentation.theme.ScreenLakeTheme
import com.example.screen_lake.presentation.viewmodels.WorkAppAppListOnBoardingScreenUiEvents
import com.example.screen_lake.presentation.viewmodels.WorkAppListOnboardingScreenState
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
class WorkAppsOnboardingScreenTest {
    @get:Rule
    val composeTestRule =  createComposeRule()
    private lateinit var context: Context

    private val workApps:ArrayList<AppInfo> = arrayListOf()

    @Before
    fun init(){
        context = ApplicationProvider.getApplicationContext()
        workApps.apply {
            clear()
            addAll(listOf(
                AppInfo(apk = "com.google.chrome","Chrome", appPrimaryUse = AppUse.WORK.key, isChecked = true),
                AppInfo(apk = "com.google.notes","Notes"),
                AppInfo(apk = "com.google.gmail","Gmail",appPrimaryUse = AppUse.WORK.key, isChecked = true),
                AppInfo(apk = "com.camera.camera","Camera", appPrimaryUse = AppUse.WORK.key, isChecked = true),
                AppInfo(apk = "com.tiktok.tiktok","TikTok"),
                AppInfo(apk = "com.player.vlc","VLC"),
                AppInfo(apk = "com.xaiomi.mi_music","Mi Music"),
                AppInfo(apk = "com.meta.snapchat","Snapchat", appPrimaryUse = AppUse.WORK.key, isChecked = true),
                AppInfo(apk = "com.meta.facebook","Facebook", appPrimaryUse = AppUse.WORK.key, isChecked = true),
                AppInfo(apk = "com.meta.instagram","Instagram", appPrimaryUse = AppUse.WORK.key, isChecked = true),
                AppInfo(apk = "com.stream.netflix","Netflix"),
                AppInfo(apk = "com.adobe.photoshop","PhotoShop", appPrimaryUse = AppUse.WORK.key, isChecked = true),
                AppInfo(apk = "com.adobe.lightroom","LightRoom", appPrimaryUse = AppUse.WORK.key, isChecked = true),
            ))
        }
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun BottomSheetIsExpanded_WhenOnBoardingTrackerStepIs__WORK_APP_BOTTOMSHEET_SCREEN_STEP(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                WorkAppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(1,started = true, OnboardingTrackStep.WORK_APP_BOTTOMSHEET_SCREEN_STEP.step),
                    dataState = MutableStateFlow(WorkAppListOnboardingScreenState()).asStateFlow(),
                    uiEvents = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(ONBOARDING_BOTTOM_SHEET_TEST_TAG).assertIsDisplayed()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun BottomSheetIsCollapsed_WhenOnBoardingTrackerStepIs__WORK_APP_SCREEN_STEP(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                WorkAppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(1,started = true, OnboardingTrackStep.WORK_APP_SCREEN_STEP.step),
                    dataState = MutableStateFlow(WorkAppListOnboardingScreenState()).asStateFlow(),
                    uiEvents = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>().asSharedFlow(),
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
                WorkAppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(1,started = false, OnboardingTrackStep.WORK_APP_SCREEN_STEP.step),
                    dataState = MutableStateFlow(WorkAppListOnboardingScreenState().copy(searchText = chrome)).asStateFlow(),
                    uiEvents = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>().asSharedFlow(),
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
                WorkAppMainBodyContent(
                    bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
                    state = WorkAppListOnboardingScreenState(
                        filteredList = emptyList(),
                        workAppsList = emptyList()
                    ), modifier = Modifier
                ) {

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
                WorkAppMainBodyContent(
                    bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
                    state = WorkAppListOnboardingScreenState(
                        filteredList = emptyList(),
                        workAppsList = emptyList()
                    ), modifier = Modifier
                ) {

                }
            }
        }
        Truth.assertThat(composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG).onChildren()).isNotEqualTo(ZERO)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckSearchTextIsNotEmptyAndFilterItemIsCorrect(){
        val chrome ="Chrome"
        val filteredList = workApps.filter {
            it.realAppName.equals(chrome, ignoreCase = true)
        }
        composeTestRule.setContent {
            ScreenLakeTheme {
                WorkAppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(
                        ONE,
                        started = true,
                        OnboardingTrackStep.WORK_APP_SCREEN_STEP.step
                    ),
                    dataState = MutableStateFlow(
                        WorkAppListOnboardingScreenState(
                            searchText = chrome, filteredList = filteredList, workAppsList = workApps
                        )
                    ).asStateFlow(),
                    uiEvents = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onAllNodesWithText(chrome).assertCountEquals(2)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckSearchTextIsNotEmptyAndFilterItemIsIncorrect(){
        val chrome ="Chromewe"
        val filteredList = workApps.filter {
            it.realAppName.equals(chrome, ignoreCase = true)
        }
        composeTestRule.setContent {
            ScreenLakeTheme {
                WorkAppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(
                        ONE,
                        started = true,
                        OnboardingTrackStep.WORK_APP_SCREEN_STEP.step
                    ),
                    dataState = MutableStateFlow(
                        WorkAppListOnboardingScreenState(
                            searchText = chrome, filteredList = filteredList, workAppsList = workApps
                        )
                    ).asStateFlow(),
                    uiEvents = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>().asSharedFlow(),
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
                WorkAppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(ONE,started = true, OnboardingTrackStep.WORK_APP_SCREEN_STEP.step),
                    dataState = MutableStateFlow(WorkAppListOnboardingScreenState().copy(filteredList = workApps, workAppsList = workApps, expandedList = false)).asStateFlow() ,
                    uiEvents = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(SHOW_MORE_OR_LESS_TEST_TAG).assertIsDisplayed()
        composeTestRule.onNodeWithTag(SHOW_MORE_OR_LESS_TEST_TAG).assertTextEquals("Show more apps (${workApps.size-FIVE})")
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckShowMoreTestWithNonEmptySearchText(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                WorkAppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(ONE,started = true, OnboardingTrackStep.WORK_APP_SCREEN_STEP.step),
                    dataState = MutableStateFlow(WorkAppListOnboardingScreenState().copy(searchText = "Chrome")).asStateFlow() ,
                    uiEvents = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>().asSharedFlow(),
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
                WorkAppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(ONE,started = true, OnboardingTrackStep.WORK_APP_SCREEN_STEP.step),
                    dataState = MutableStateFlow(WorkAppListOnboardingScreenState().copy(workAppsList = workApps.subList(ZERO,TWO))).asStateFlow() ,
                    uiEvents = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(SHOW_MORE_OR_LESS_TEST_TAG).assertDoesNotExist()
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun NextButtonTextUpdatesCorrectlyForCheckedItems(){
        val checkedList = workApps.filter { it.isChecked }
        composeTestRule.setContent {
            ScreenLakeTheme {
                WorkAppListOnboardingScreen(
                    navHostController = rememberNavController(),
                    onboardingTracker = OnboardingTracker(ONE,started = true, OnboardingTrackStep.WORK_APP_SCREEN_STEP.step),
                    dataState = MutableStateFlow(WorkAppListOnboardingScreenState().copy(checkedItems = checkedList.size)).asStateFlow() ,
                    uiEvents = MutableSharedFlow<WorkAppAppListOnBoardingScreenUiEvents>().asSharedFlow(),
                    onEvent = {}
                )
            }
        }
        composeTestRule.onNodeWithTag(ONBOARDING_NEXT_BUTTON_TEST_TAG).assertTextEquals("Next (${checkedList.size})")
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckLazyColumnIfCheckedAreCorrect(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                WorkAppMainBodyContent(
                    bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
                    state = WorkAppListOnboardingScreenState(
                        filteredList = workApps,
                        workAppsList = workApps,
                        expandedList = false
                    ), modifier = Modifier
                ) {

                }
            }
        }
        val subList = workApps.subList(ZERO,FIVE)
        composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG).onChildren().assertCountEquals(subList.size)
        composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG,useUnmergedTree = true).onChildren().assertAny(
            hasTestTag(CHECKED_ICON_TEST_TAG)
        )
        composeTestRule.onAllNodesWithTag(CHECKED_ICON_TEST_TAG,useUnmergedTree = true).assertCountEquals(subList.filter { it.isChecked }.size)
    }

    @OptIn(ExperimentalMaterialApi::class)
    @Test
    fun CheckLazyColumnIfCheckedAreCorrectWithExpandedList(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                WorkAppMainBodyContent(
                    bottomSheetScaffoldState = rememberBottomSheetScaffoldState(),
                    state = WorkAppListOnboardingScreenState(
                        filteredList = workApps,
                        workAppsList = workApps,
                        expandedList = true
                    ), modifier = Modifier
                ) {

                }
            }
        }
        composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG).onChildren().assertCountEquals(workApps.size)
        composeTestRule.onAllNodesWithTag(CHECKED_ICON_TEST_TAG,useUnmergedTree = true).assertCountEquals(workApps.filter { it.isChecked }.size)
    }

}