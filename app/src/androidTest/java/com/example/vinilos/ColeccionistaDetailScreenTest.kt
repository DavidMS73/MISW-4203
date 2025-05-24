package com.example.vinilos

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.vinilos.data.datasources.mock.MockCollectorsApiService
import com.example.vinilos.data.repositories.CollectorRepository
import com.example.vinilos.ui.screens.ColeccionistaDetailScreen
import com.example.vinilos.ui.viewmodels.CollectorDetailViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class ColeccionistaDetailScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var navController: TestNavHostController
    private val collectorId = 1

    @OptIn(ExperimentalTestApi::class)
    @Before
    fun setUp() {
        val viewModel = CollectorDetailViewModel(
            collectorRepository = CollectorRepository(MockCollectorsApiService),
            id = collectorId,
        )

        composeTestRule.setContent {
            navController = TestNavHostController(LocalContext.current).apply {
                navigatorProvider.addNavigator(ComposeNavigator())
            }
            val collectorUiState = viewModel.uiState.collectAsState().value
            ColeccionistaDetailScreen(
                navController = navController,
                viewModel = viewModel,
                collectorUiState = collectorUiState,
                collectorId = collectorId,
            )
        }

        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("CollectorDetailContent"),
            8000L
        )
    }

    @Test
    fun collectorDetail_name_isDisplayed() {
        composeTestRule
            .onNodeWithText("Usuario 1")
            .assertIsDisplayed()
    }

    @Test
    fun collectorDetail_phone_isCorrectlyFormatted() {
        composeTestRule
            .onNodeWithText("Teléfono: 3002182532")
            .assertIsDisplayed()
    }

    @Test
    fun collectorDetail_email_isCorrectlyFormatted() {
        composeTestRule
            .onNodeWithText("Correo: usuario1@example.com")
            .assertIsDisplayed()
    }
}