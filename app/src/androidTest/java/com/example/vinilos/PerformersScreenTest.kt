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
import com.example.vinilos.data.datasources.mock.MockPerformersApiService
import com.example.vinilos.data.datasources.mock.mockPerformers
import com.example.vinilos.data.repositories.PerformerRepository
import com.example.vinilos.ui.screens.ArtistasScreen
import com.example.vinilos.ui.viewmodels.PerformersViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalTestApi::class)
class PerformersScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    private lateinit var viewModel: PerformersViewModel

    @Before
    fun setUp() {
        viewModel = PerformersViewModel(
            performerRepository = PerformerRepository(MockPerformersApiService)
        )
        composeTestRule.setContent {
            val uiState = viewModel.uiState.collectAsState().value
            ArtistasScreen(
                viewModel = viewModel,
                performersUiState = uiState
            )
        }
        composeTestRule.waitUntilAtLeastOneExists(
            hasTestTag("PerformersSuccessScreen"),
            8000L
        )
    }

    @Test
    fun artistasScreen_verifyContent() {
        for (performer in mockPerformers) {
            composeTestRule.onNodeWithText(performer.name).assertIsDisplayed()
        }
    }

    @Test
    fun artistasScreen_searchForPerformer_findsPerformer() {
        val searchInput = composeTestRule.onNodeWithTag("PerformersSearchTextField")
        searchInput.assertIsDisplayed()
        val lookedUpPerformer = mockPerformers[0]
        searchInput.performTextInput(lookedUpPerformer.name)
        Espresso.closeSoftKeyboard()
        composeTestRule.onNodeWithTag("PerformerCard-${lookedUpPerformer.id}").assertIsDisplayed()

        val remainingPerformers = mockPerformers.subList(1, mockPerformers.size)
        for (performer in remainingPerformers) {
            composeTestRule.onNodeWithText(performer.name).assertDoesNotExist()
        }
    }

    @Test
    fun albumesScreen_searchForPerformer_findNoPerformer() {
        val searchInput = composeTestRule.onNodeWithTag("PerformersSearchTextField")
        searchInput.assertIsDisplayed()
        val lookedUpPerformer = "No performer"
        searchInput.performTextInput(lookedUpPerformer)
        Espresso.closeSoftKeyboard()
        // Validate only text field has this text
        composeTestRule.onAllNodes(hasText(lookedUpPerformer)).assertCountEquals(1)
    }

    @Test
    fun albumesScreen_searchForPerformer_clearSearch() {
        val searchInput = composeTestRule.onNodeWithTag("PerformersSearchTextField")
        searchInput.assertIsDisplayed()
        val lookedUpPerformer = mockPerformers[0]
        searchInput.performTextInput(lookedUpPerformer.name)
        Espresso.closeSoftKeyboard()
        composeTestRule.onNodeWithTag("PerformerCard-${lookedUpPerformer.id}").assertIsDisplayed()

        val clearButton = composeTestRule.onNodeWithTag("ClearButton")
        clearButton.assertIsDisplayed()
        clearButton.performClick()
        for (performer in mockPerformers) {
            composeTestRule.onNodeWithText(performer.name).assertIsDisplayed()
        }
    }
}