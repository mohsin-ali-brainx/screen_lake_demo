package com.example.screen_lake.presentation.screens.onboarding.behaviorOnboarding

import android.content.Context
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.ui.Modifier
import androidx.compose.ui.test.assertAll
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onChildren
import androidx.compose.ui.test.onNodeWithTag
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.screen_lake.R
import com.example.screen_lake.appUtils.Constants.IntegerConstants.FOUR
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ONE
import com.example.screen_lake.appUtils.Constants.IntegerConstants.TWO
import com.example.screen_lake.appUtils.Constants.IntegerConstants.ZERO
import com.example.screen_lake.appUtils.Constants.TestTags.MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG
import com.example.screen_lake.appUtils.enums.AppBehaviors
import com.example.screen_lake.domain.models.Behavior
import com.example.screen_lake.presentation.screens.onboarding.behaviourOnboarding.BehaviorMainBodyContent
import com.example.screen_lake.presentation.theme.ScreenLakeTheme
import com.example.screen_lake.presentation.viewmodels.BehaviorOnboardingScreenState
import com.google.common.truth.Truth
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BehaviorOnboardingScreenTest {

    @get:Rule
    val composeTestRule =  createComposeRule()
    private lateinit var context: Context

    private val behaviorList:ArrayList<Behavior> = arrayListOf()

    @Before
    fun init(){
        context = ApplicationProvider.getApplicationContext<Context>()
        behaviorList.apply {
            clear()
            context.apply {
                add(Behavior(getString(R.string.doom_scrolling), AppBehaviors.WANT_LESS.importance))
                add(Behavior(getString(R.string.habitual_pick_ups), AppBehaviors.STOP_THIS.importance))
                add(Behavior(getString(R.string.gaming), AppBehaviors.NOT_A_PROBLEM.importance))
                add(Behavior(getString(R.string.watching_videos), AppBehaviors.WANT_LESS.importance))
                add(Behavior(getString(R.string.viewing_adult_content), AppBehaviors.WANT_LESS.importance))
                add(Behavior(getString(R.string.texting_too_much), AppBehaviors.STOP_THIS.importance))
                add(Behavior(getString(R.string.calling_too_much), AppBehaviors.WANT_LESS.importance))
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun CheckLazyColumnIsEmpty(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                BehaviorMainBodyContent(
                    modifier = Modifier,
                    state = BehaviorOnboardingScreenState()
                ) {

                }
            }
        }
        composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG).onChildren().assertCountEquals(ZERO)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Test
    fun CheckLazyColumnIsNotEmpty(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                BehaviorMainBodyContent(
                    modifier = Modifier,
                    state = BehaviorOnboardingScreenState(appBehaviors = behaviorList)
                ) {

                }
            }
        }
        Truth.assertThat(composeTestRule.onNodeWithTag(MAIN_CONTENT_BODY_LAZY_COLUMN_TEST_TAG).onChildren()).isNotEqualTo(ZERO)
    }

    @Test
    fun CheckLazyColumnIfImportanceVisible(){
        composeTestRule.setContent {
            ScreenLakeTheme {
                BehaviorMainBodyContent(
                    modifier = Modifier,
                    state = BehaviorOnboardingScreenState(appBehaviors = behaviorList)
                ) {

                }
            }
        }
        composeTestRule.onAllNodesWithText(AppBehaviors.WANT_LESS.behaviorName).assertAll(
            hasText(AppBehaviors.WANT_LESS.behaviorName)
        ).assertCountEquals(FOUR)
        composeTestRule.onAllNodesWithText(AppBehaviors.STOP_THIS.behaviorName).assertAll(
            hasText(AppBehaviors.STOP_THIS.behaviorName)
        ).assertCountEquals(TWO)
        composeTestRule.onAllNodesWithText(AppBehaviors.NOT_A_PROBLEM.behaviorName).assertAll(
            hasText(AppBehaviors.NOT_A_PROBLEM.behaviorName)
        ).assertCountEquals(ONE)
    }
}