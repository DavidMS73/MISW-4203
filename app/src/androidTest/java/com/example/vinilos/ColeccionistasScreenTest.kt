package com.example.vinilos

import androidx.compose.runtime.collectAsState
import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.espresso.Espresso
import com.example.vinilos.data.datasources.mock.MockCollectorsApiService
import com.example.vinilos.data.datasources.mock.mockCollectors
import com.example.vinilos.data.repositories.CollectorRepository
import com.example.vinilos.ui.screens.ColeccionistasScreen
import com.example.vinilos.ui.viewmodels.CollectorsViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class ColeccionistasScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: CollectorsViewModel

    @Before
    fun setUp() {
        viewModel = CollectorsViewModel(
            collectorsRepository = CollectorRepository(MockCollectorsApiService)
        )
        composeTestRule.setContent {
            val uiState = viewModel.uiState.collectAsState().value
            ColeccionistasScreen(
                viewModel = viewModel,
                collectorsUiState = uiState,
                onCollectorClick = {}
            )
        }
        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("CollectorsSuccessScreen"),
            8000L
        )
    }

    @Test
    fun collectorsScreen_verifyContent() {
        for (collector in mockCollectors) {
            composeTestRule.onNodeWithText(collector.name).assertIsDisplayed()
        }
    }

    @Test
    fun collectorScreen_searchForCollector_findsCollector() {
        val searchInput = composeTestRule.onNodeWithTag("SearchTextField")
        searchInput.assertIsDisplayed()
        val lookedUpCollector = mockCollectors[0]
        searchInput.performTextInput(lookedUpCollector.name)
        Espresso.closeSoftKeyboard()
        composeTestRule.onNodeWithTag("CollectorCard-${lookedUpCollector.id}").assertIsDisplayed()

        val remainingCollectors = mockCollectors.subList(1, mockCollectors.size)
        for (collector in remainingCollectors) {
            composeTestRule.onNodeWithText(collector.name).assertDoesNotExist()
        }
    }

    @Test
    fun CollectorScreen_searchForCollector_findNoCollector() {
        val searchInput = composeTestRule.onNodeWithTag("SearchTextField")
        searchInput.assertIsDisplayed()
        val lookedUpCollector = "No collector"
        searchInput.performTextInput(lookedUpCollector)
        Espresso.closeSoftKeyboard()
        // Validate only text field has this text
        composeTestRule.onAllNodes(hasText(lookedUpCollector)).assertCountEquals(1)
    }

    @Test
    fun collectorScreen_searchForCollector_clearSearch() {
        val searchInput = composeTestRule.onNodeWithTag("SearchTextField")
        searchInput.assertIsDisplayed()
        val lookedUpCollector = mockCollectors[0]
        searchInput.performTextInput(lookedUpCollector.name)
        Espresso.closeSoftKeyboard()
        composeTestRule.onNodeWithTag("CollectorCard-${lookedUpCollector.id}").assertIsDisplayed()

        val clearButton = composeTestRule.onNodeWithTag("ClearButton")
        clearButton.assertIsDisplayed()
        clearButton.performClick()
        for (collector in mockCollectors) {
            composeTestRule.onNodeWithText(collector.name).assertIsDisplayed()
        }
    }
}