package com.example.vinilos

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.vinilos.data.datasources.mock.MockPerformersApiService
import com.example.vinilos.data.repositories.PerformerRepository
import com.example.vinilos.ui.screens.PerformerDetailScreen
import com.example.vinilos.ui.viewmodels.PerformerDetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class PerformerDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private val performerId = 100

    @OptIn(ExperimentalTestApi::class)
    @Before
    fun setUp() {
        val viewModel = PerformerDetailViewModel(
            performerRepository = PerformerRepository(MockPerformersApiService),
            id = performerId,
        )

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            val collectorUiState = viewModel.uiState.collectAsState().value
            PerformerDetailScreen(
                navController = navController,
                viewModel = viewModel,
                performerUiState = collectorUiState,
                performerId = performerId,
                onAlbumTap = {}
            )
        }

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("PerformerDetailSuccessScreen"),
            8000L
        )
    }

    @Test
    fun performerDetailScreen_verifyNameIsDisplayed() {
        composeTestRule
            .onNodeWithText("Ruben Blades")
            .assertIsDisplayed()
    }

    @Test
    fun performerDetailScreen_verifyAlbumsAreDisplayed() {
        composeTestRule.onNodeWithTag("PerformerAlbumsList").assertIsDisplayed()
    }

    @Test
    fun performerDetailScreen_verifyImageIsDisplayed() {
        composeTestRule.onNodeWithTag("PerformerDetailImage").assertIsDisplayed()
    }
}